package org.example;

import org.example.model.Reservation;
import org.example.service.BookingQueueService;
import org.example.service.RoomInventory;
import org.example.service.SearchService;

import java.util.List;

/**
 * BookMyStay demo driver — cumulative through USE CASE 3.
 */
public class App {
    public static void main(String[] args) {
        // ---------- UC1: inventory ----------
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10, 1500.0, List.of("WiFi", "TV"));
        inventory.addRoomType("Double", 6, 2500.0, List.of("WiFi", "TV", "Mini Bar"));
        inventory.addRoomType("Suite", 3, 6500.0, List.of("WiFi", "TV", "Mini Bar", "Jacuzzi"));

        // ---------- UC2: search ----------
        System.out.println("########## USE CASE 2: Search ##########\n");
        SearchService search = new SearchService(inventory);
        search.displayAvailableRooms();

        // ---------- UC3: FCFS booking queue ----------
        System.out.println("\n########## USE CASE 3: Booking Requests (FCFS) ##########\n");
        BookingQueueService queue = new BookingQueueService();
        queue.enqueue(new Reservation("R001", "Arjun",  "Suite",  2));
        queue.enqueue(new Reservation("R002", "Bhavya", "Double", 1));
        queue.enqueue(new Reservation("R003", "Charan", "Single", 3));
        queue.enqueue(new Reservation("R004", "Divya",  "Suite",  1));

        System.out.println("\nRequests waiting in queue: " + queue.size());
        System.out.println("Next to be served (peek): " + queue.peek().getReservationId());
    }
}