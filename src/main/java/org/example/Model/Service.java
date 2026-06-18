package org.example.Model;

/**
 * An optional add-on service that can be attached to a reservation
 * (e.g. Breakfast, Airport Pickup, Spa).
 */
public class Service {

    private final String name;
    private final double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName()  { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " (Rs." + String.format("%.2f", price) + ")";
    }
}