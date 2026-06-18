package org.example.model;

/**
 * A booking request that travels through the system.
 * Starts as PENDING in the queue (UC3); gains a room ID, cost and
 * CONFIRMED status at allocation (UC4); accrues service cost in UC5.
 */
public class Reservation {

    private final String reservationId;
    private final String guestName;
    private final String roomType;
    private final int nights;

    private String roomId;          // assigned at confirmation (UC4)
    private double roomCost;         // nights * price (UC4)
    private double serviceCost;      // add-on services (UC5)
    private String status;           // PENDING | CONFIRMED | REJECTED | CANCELLED

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = Math.max(1, nights);
        this.status = "PENDING";
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName()     { return guestName; }
    public String getRoomType()      { return roomType; }
    public int    getNights()        { return nights; }

    public String getRoomId()        { return roomId; }
    public void   setRoomId(String roomId) { this.roomId = roomId; }

    public double getRoomCost()      { return roomCost; }
    public void   setRoomCost(double roomCost) { this.roomCost = roomCost; }

    public double getServiceCost()   { return serviceCost; }
    public void   setServiceCost(double serviceCost) { this.serviceCost = serviceCost; }

    public double getTotalCost()     { return roomCost + serviceCost; }

    public String getStatus()        { return status; }
    public void   setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %-12s %-8s x%dn  room=%-9s status=%-9s total=Rs.%.2f",
                reservationId, guestName, roomType, nights,
                roomId == null ? "-" : roomId, status, getTotalCost());
    }
}