package org.example;

import org.example.model.Reservation;
import org.example.model.Service;
import org.example.service.BookingQueueService;
import org.example.service.BookingService;
import org.example.service.RoomInventory;
import org.example.service.ServiceManagement;

import java.util.List;

/**
 * BookMyStay demo driver — cumulative through USE CASE 5.
 */
public class App {
    public static void main(String[] args) {
        // ---------- UC1: inventory ----------
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10, 1500.0, List.of("WiFi", "TV"));
        inventory.addRoomType("Double", 6, 2500.0, List.of("WiFi", "TV", "Mini Bar"));
        inventory.addRoomType("Suite", 3, 6500.0, List.of("WiFi", "TV", "Jacuzzi"));

        // ---------- UC3: queue ----------
        BookingQueueService queue = new BookingQueueService();
        Reservation r1 = new Reservation("R001", "Arjun",  "Suite",  2);
        Reservation r2 = new Reservation("R002", "Bhavya", "Double", 1);
        queue.enqueue(r1);
        queue.enqueue(r2);

        // ---------- UC4: confirm ----------
        System.out.println("\n########## USE CASE 4: Confirmation ##########\n");
        BookingService booking = new BookingService(inventory, queue);
        booking.processAll();

        // ---------- UC5: add-on services ----------
        System.out.println("\n########## USE CASE 5: Add-On Services ##########\n");
        ServiceManagement services = new ServiceManagement();
        Service breakfast = new Service("Breakfast", 400.0);
        Service pickup    = new Service("Airport Pickup", 1200.0);
        Service spa       = new Service("Spa", 2000.0);

        services.addService(r1, breakfast);
        services.addService(r1, spa);
        services.addService(r2, pickup);

        System.out.println();
        services.displayServices("R001");
        services.displayServices("R002");

        System.out.println("\nFinal bills:");
        System.out.println("  " + r1);
        System.out.println("  " + r2);
    }
}