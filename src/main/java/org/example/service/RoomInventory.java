package org.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * USE CASE 1 + 2 + 4 — Room Inventory.
 */
public class RoomInventory {

    private final Map<String, Integer> roomCounts = new HashMap<>();
    private final Map<String, Double> roomPrices = new HashMap<>();
    private final Map<String, List<String>> roomAmenities = new HashMap<>();

    public void addRoomType(String type, int count, double pricePerNight) {
        addRoomType(type, count, pricePerNight, new ArrayList<>());
    }

    public void addRoomType(String type, int count, double pricePerNight, List<String> amenities) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }
        if (count < 0 || pricePerNight < 0) {
            throw new IllegalArgumentException("Count/price cannot be negative");
        }
        roomCounts.put(type, count);
        roomPrices.put(type, pricePerNight);
        roomAmenities.put(type, amenities == null ? new ArrayList<>() : amenities);
    }

    public void updateCount(String type, int newCount) {
        requireType(type);
        if (newCount < 0) throw new IllegalArgumentException("Count cannot be negative");
        roomCounts.put(type, newCount);
    }

    public void updatePrice(String type, double newPrice) {
        requireType(type);
        if (newPrice < 0) throw new IllegalArgumentException("Price cannot be negative");
        roomPrices.put(type, newPrice);
    }

    /** Atomically reduce the available count by one when a room is allocated (UC4). */
    public synchronized boolean decrementCount(String type) {
        int current = getAvailableCount(type);
        if (current <= 0) return false;
        roomCounts.put(type, current - 1);
        return true;
    }

    public int getAvailableCount(String type) {
        return roomCounts.getOrDefault(type, 0);
    }

    public double getPrice(String type) {
        return roomPrices.getOrDefault(type, 0.0);
    }

    public List<String> getAmenities(String type) {
        return roomAmenities.getOrDefault(type, new ArrayList<>());
    }

    public boolean hasRoomType(String type) {
        return roomCounts.containsKey(type);
    }

    public Set<String> getRoomTypes() {
        return roomCounts.keySet();
    }

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