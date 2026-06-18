package org.example.service;

import java.util.ArrayList;
import java.util.List;

/**
 * USE CASE 2 — Room Search & Availability Check.
 *
 * READ-ONLY view over RoomInventory. It never mutates inventory; it only
 * filters and presents what is currently available, with defensive checks.
 */
public class SearchService {

    private final RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /** Returns the list of room types that currently have at least one room free. */
    public List<String> getAvailableRoomTypes() {
        List<String> available = new ArrayList<>();
        for (String type : inventory.getRoomTypes()) {
            if (inventory.getAvailableCount(type) > 0) {
                available.add(type);
            }
        }
        return available;
    }

    /** Defensive availability check used before any booking is attempted. */
    public boolean isAvailable(String type) {
        return inventory.hasRoomType(type) && inventory.getAvailableCount(type) > 0;
    }

    /** Display search results: only available rooms, with pricing and amenities. */
    public void displayAvailableRooms() {
        System.out.println("=== Search Results (available rooms) ===");
        List<String> available = getAvailableRoomTypes();
        if (available.isEmpty()) {
            System.out.println("Sorry, no rooms are currently available.");
        } else {
            for (String type : available) {
                System.out.printf("%-8s | %2d left | Rs.%.2f/night | amenities: %s%n",
                        type,
                        inventory.getAvailableCount(type),
                        inventory.getPrice(type),
                        String.join(", ", inventory.getAmenities(type)));
            }
        }
        System.out.println("========================================");
    }
}