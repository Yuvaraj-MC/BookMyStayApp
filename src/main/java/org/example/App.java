package org.example;

import org.example.service.RoomInventory;
import org.example.service.SearchService;

import java.util.List;

/**
 * BookMyStay demo driver — cumulative through USE CASE 2.
 */
public class App {
    public static void main(String[] args) {
        // ---------- UC1: inventory ----------
        System.out.println("########## USE CASE 1: Room Inventory Setup ##########\n");
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 10, 1500.0, List.of("WiFi", "TV"));
        inventory.addRoomType("Double", 6, 2500.0, List.of("WiFi", "TV", "Mini Bar"));
        inventory.addRoomType("Suite", 0, 6500.0, List.of("WiFi", "TV", "Mini Bar", "Jacuzzi"));
        inventory.displayInventory();

        // ---------- UC2: search ----------
        System.out.println("\n########## USE CASE 2: Search & Availability ##########\n");
        SearchService search = new SearchService(inventory);
        search.displayAvailableRooms(); // Suite has 0 -> excluded from results

        System.out.println("\nIs 'Suite' available? " + search.isAvailable("Suite"));
        System.out.println("Is 'Double' available? " + search.isAvailable("Double"));
    }
}