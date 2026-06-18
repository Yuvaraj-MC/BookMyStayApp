package org.example;

import org.example.model.Reservation;
import org.example.Model.Service;
import org.example.service.BookingQueueService;
import org.example.service.BookingService;
import org.example.service.ReportingService;
import org.example.service.RoomInventory;
import org.example.service.SearchService;
import org.example.service.ServiceManagement;

import java.util.List;

/**
 * BookMyStay demo driver — FULL SYSTEM (UC1 .. UC6).
 */
public class App {
    public static void main(String[] args) {
        // ---------- UC1: inventory ----------
        System.out.println("########## UC1: Inventory ##########\n");
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10, 1500.0, List.of("WiFi", "TV"));
        inventory.addRoomType("Double", 6, 2500.0, List.of("WiFi", "TV", "Mini Bar"));
        inventory.addRoomType("Suite", 2, 6500.0, List.of("WiFi", "TV", "Jacuzzi"));
        inventory.displayInventory();

        // ---------- UC2: search ----------
        System.out.println("\n########## UC2: Search ##########\n");
        SearchService search = new SearchService(inventory);
        search.displayAvailableRooms();

        // ---------- UC3: FCFS queue ----------
        System.out.println("\n########## UC3: Booking Queue (FCFS) ##########\n");
        BookingQueueService queue = new BookingQueueService();
        Reservation r1 = new Reservation("R001", "Arjun",  "Suite",  2);
        Reservation r2 = new Reservation("R002", "Bhavya", "Double", 1);
        Reservation r3 = new Reservation("R003", "Charan", "Suite",  1);
        Reservation r4 = new Reservation("R004", "Divya",  "Suite",  3); // 3rd suite -> rejected
        queue.enqueue(r1); queue.enqueue(r2); queue.enqueue(r3); queue.enqueue(r4);

        // ---------- UC6 wiring + UC4: confirm ----------
        System.out.println("\n########## UC4: Confirmation & Allocation ##########\n");
        ReportingService reporting = new ReportingService();
        BookingService booking = new BookingService(inventory, queue);
        booking.setReportingService(reporting);
        booking.processAll();

        // ---------- UC5: add-on services ----------
        System.out.println("\n########## UC5: Add-On Services ##########\n");
        ServiceManagement services = new ServiceManagement();
        services.addService(r1, new Service("Breakfast", 400.0));
        services.addService(r1, new Service("Spa", 2000.0));
        services.addService(r2, new Service("Airport Pickup", 1200.0));

        // ---------- UC6: report, cancel, report again ----------
        System.out.println("\n########## UC6: History & Reporting ##########\n");
        reporting.generateReport();

        System.out.println("\n-- Arjun cancels R001 --\n");
        booking.cancel("R001");

        System.out.println("\nReport after cancellation:");
        reporting.generateReport();

        System.out.println("\nInventory after cancellation (Suite freed):");
        inventory.displayInventory();
    }
}