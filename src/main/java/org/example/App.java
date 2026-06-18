package org.example;

import org.example.service.RoomInventory;

/**
 * BookMyStay demo driver.
 * USE CASE 1 — set up and manage the room inventory.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("########## USE CASE 1: Room Inventory Setup ##########\n");

        RoomInventory inventory = new RoomInventory();

        // Initialize room types (Single, Double, Suite)
        inventory.addRoomType("Single", 10, 1500.0);
        inventory.addRoomType("Double", 6, 2500.0);
        inventory.addRoomType("Suite", 3, 6000.0);

        inventory.displayInventory();

        // Dynamic update: hotel added 2 more Single rooms, Suite price revised
        System.out.println("\n-- Updating inventory: +2 Single rooms, Suite price -> 6500 --\n");
        inventory.updateCount("Single", 12);
        inventory.updatePrice("Suite", 6500.0);

        inventory.displayInventory();
    }
}