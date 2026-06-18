package org.example;

import org.example.model.Reservation;
import org.example.service.BookingQueueService;
import org.example.service.BookingService;
import org.example.service.RoomInventory;
import org.example.service.SearchService;

import java.util.List;

/**
 * BookMyStay demo driver — cumulative through USE CASE 4.
 */
public class App {
    public static void main(String[] args) {
        // ---------- UC1: inventory ----------
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10, 1500.0, List.of("WiFi", "TV"));
        inventory.addRoomType("Double", 6, 2500.0, List.of("WiFi", "TV", "Mini Bar"));
        inventory.addRoomType("Suite", 1, 6500.0, List.of("WiFi", "TV", "Jacuzzi")); // only 1 suite!

        // ---------- UC2: search ----------
        SearchService search = new SearchService(inventory);

        // ---------- UC3: queue ----------
        BookingQueueService queue = new BookingQueueService();
        queue.enqueue(new Reservation("R001", "Arjun",  "Suite",  2));
        queue.enqueue(new Reservation("R002", "Bhavya", "Double", 1));
        queue.enqueue(new Reservation("R003", "Charan", "Single", 3));
        queue.enqueue(new Reservation("R004", "Divya",  "Suite",  1)); // 2nd suite -> rejected

        // ---------- UC4: confirm & allocate ----------
        System.out.println("\n########## USE CASE 4: Confirmation & Allocation ##########\n");
        BookingService booking = new BookingService(inventory, queue);
        booking.processAll();

        System.out.println("\nAll assigned room IDs (unique): " + booking.getBookedRoomIds());
        System.out.println("\nInventory after bookings:");
        inventory.displayInventory();
    }
}