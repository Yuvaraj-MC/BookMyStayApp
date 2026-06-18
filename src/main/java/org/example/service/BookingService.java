package org.example.service;

import org.example.model.Reservation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * USE CASE 4 + 6 — Reservation Confirmation, Allocation & Cancellation.
 */
public class BookingService {

    private final RoomInventory inventory;
    private final BookingQueueService queue;

    private final Set<String> bookedRoomIds = new HashSet<>();
    private final Map<String, Set<String>> typeToRooms = new HashMap<>();
    private final Map<String, Reservation> confirmedById = new HashMap<>();
    private ReportingService reporting;   // UC6 (optional)

    public BookingService(RoomInventory inventory, BookingQueueService queue) {
        this.inventory = inventory;
        this.queue = queue;
    }

    /** UC6: attach a reporting service so confirmed bookings are recorded to history. */
    public void setReportingService(ReportingService reporting) {
        this.reporting = reporting;
    }

    /** Process the next request in the queue and try to confirm it. */
    public Reservation processNext() {
        Reservation r = queue.dequeue();
        if (r == null) return null;

        String type = r.getRoomType();

        if (inventory.getAvailableCount(type) <= 0) {
            r.setStatus("REJECTED");
            System.out.printf("REJECTED -> %s (%s): no '%s' rooms left%n",
                    r.getReservationId(), r.getGuestName(), type);
            return r;
        }

        String roomId = allocateRoomId(type);   // unique ID, never reused
        bookedRoomIds.add(roomId);
        typeToRooms.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
        inventory.decrementCount(type);          // instant inventory sync

        r.setRoomId(roomId);
        r.setRoomCost(inventory.getPrice(type) * r.getNights());
        r.setStatus("CONFIRMED");
        confirmedById.put(r.getReservationId(), r);
        if (reporting != null) reporting.record(r);   // UC6: persist to history

        System.out.printf("CONFIRMED-> %s (%s): room %s, Rs.%.2f%n",
                r.getReservationId(), r.getGuestName(), roomId, r.getRoomCost());
        return r;
    }

    /** Drain the whole queue, confirming each request in FCFS order. */
    public void processAll() {
        while (!queue.isEmpty()) {
            processNext();
        }
    }

    /** UC6: cancel a confirmed booking — frees the room ID and restores inventory. */
    public boolean cancel(String reservationId) {
        Reservation r = confirmedById.get(reservationId);
        if (r == null || !"CONFIRMED".equals(r.getStatus())) {
            System.out.printf("Cannot cancel %s (not an active booking)%n", reservationId);
            return false;
        }
        String type = r.getRoomType();
        bookedRoomIds.remove(r.getRoomId());
        typeToRooms.getOrDefault(type, new HashSet<>()).remove(r.getRoomId());
        inventory.incrementCount(type);          // room is available again
        r.setStatus("CANCELLED");
        System.out.printf("CANCELLED-> %s: room %s released back to inventory%n",
                reservationId, r.getRoomId());
        return true;
    }

    /** Find the lowest-numbered free room ID for the type (e.g. SUITE-001). */
    private String allocateRoomId(String type) {
        Set<String> assigned = typeToRooms.getOrDefault(type, new HashSet<>());
        int capacity = inventory.getAvailableCount(type) + assigned.size();
        for (int i = 1; i <= capacity; i++) {
            String id = type.toUpperCase() + "-" + String.format("%03d", i);
            if (!bookedRoomIds.contains(id)) {
                return id;
            }
        }
        throw new IllegalStateException("No free room ID for type: " + type);
    }

    public Set<String> getBookedRoomIds() {
        return bookedRoomIds;
    }

    public Set<String> getRoomsForType(String type) {
        return typeToRooms.getOrDefault(type, new HashSet<>());
    }
}