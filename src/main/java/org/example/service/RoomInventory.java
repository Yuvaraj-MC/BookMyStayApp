package org.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * USE CASE 1 — Room Inventory Setup & Management.
 *
 * Single source of truth for hotel room inventory.
 * Two parallel HashMaps give O(1) lookup:
 *   roomCounts : room type -> available count
 *   roomPrices : room type -> price per night
 */
public class RoomInventory {

    // room type -> available count
    private final Map<String, Integer> roomCounts = new HashMap<>();
    // room type -> price per night
    private final Map<String, Double> roomPrices = new HashMap<>();

    /** Initialize a room type with its count and nightly price. */
    public void addRoomType(String type, int count, double pricePerNight) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }
        if (count < 0 || pricePerNight < 0) {
            throw new IllegalArgumentException("Count/price cannot be negative");
        }
        roomCounts.put(type, count);
        roomPrices.put(type, pricePerNight);
    }

    /** Dynamic inventory update: change the available count. */
    public void updateCount(String type, int newCount) {
        requireType(type);
        if (newCount < 0) throw new IllegalArgumentException("Count cannot be negative");
        roomCounts.put(type, newCount);
    }

    /** Dynamic inventory update: change the nightly price. */
    public void updatePrice(String type, double newPrice) {
        requireType(type);
        if (newPrice < 0) throw new IllegalArgumentException("Price cannot be negative");
        roomPrices.put(type, newPrice);
    }

    public int getAvailableCount(String type) {
        return roomCounts.getOrDefault(type, 0);
    }

    public double getPrice(String type) {
        return roomPrices.getOrDefault(type, 0.0);
    }

    public boolean hasRoomType(String type) {
        return roomCounts.containsKey(type);
    }

    public Set<String> getRoomTypes() {
        return roomCounts.keySet();
    }

    /** Real-time availability snapshot. */
    public void displayInventory() {
        System.out.println("=== Room Inventory ===");
        System.out.printf("%-10s %-10s %-12s%n", "TYPE", "AVAILABLE", "PRICE/NIGHT");
        for (String type : roomCounts.keySet()) {
            System.out.printf("%-10s %-10d Rs.%-10.2f%n",
                    type, roomCounts.get(type), roomPrices.get(type));
        }
        System.out.println("======================");
    }

    private void requireType(String type) {
        if (!roomCounts.containsKey(type)) {
            throw new IllegalArgumentException("Unknown room type: " + type);
        }
    }
}