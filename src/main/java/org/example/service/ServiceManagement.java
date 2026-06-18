package org.example.service;

import org.example.model.Reservation;
import org.example.model.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * USE CASE 5 — Add-On Service Selection.
 *
 * One-to-many mapping: reservation ID -> List<Service>.
 * A booking can carry multiple services; the module keeps the reservation's
 * serviceCost in sync so the total bill stays correct.
 */
public class ServiceManagement {

    // reservation ID -> list of attached services
    private final Map<String, List<Service>> reservationServices = new HashMap<>();

    /** Attach one service to a confirmed reservation and update its cost. */
    public void addService(Reservation reservation, Service service) {
        if (!"CONFIRMED".equals(reservation.getStatus())) {
            System.out.printf("Skip add-on for %s (status=%s)%n",
                    reservation.getReservationId(), reservation.getStatus());
            return;
        }
        reservationServices
                .computeIfAbsent(reservation.getReservationId(), k -> new ArrayList<>())
                .add(service);

        reservation.setServiceCost(getServiceCost(reservation.getReservationId()));
        System.out.printf("Added '%s' to %s%n", service.getName(), reservation.getReservationId());
    }

    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    /** Sum of all add-on prices for a reservation. */
    public double getServiceCost(String reservationId) {
        double sum = 0.0;
        for (Service s : getServices(reservationId)) {
            sum += s.getPrice();
        }
        return sum;
    }

    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);
        System.out.printf("Add-ons for %s: %s (Rs.%.2f)%n",
                reservationId,
                services.isEmpty() ? "none" : services.toString(),
                getServiceCost(reservationId));
    }
}