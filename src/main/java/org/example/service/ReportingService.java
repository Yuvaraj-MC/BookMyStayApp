package org.example.service;

import org.example.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * USE CASE 6 — Booking History & Reporting.
 *
 * Keeps an ordered List<Reservation> of every confirmed booking so the hotel
 * has a reliable audit trail, can review/cancel, and can generate reports.
 * (The list stores object references, so status changes such as CANCELLED
 *  are reflected automatically.)
 */
public class ReportingService {

    private final List<Reservation> history = new ArrayList<>();

    /** Persist a confirmed reservation into the booking history. */
    public void record(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return history;
    }

    /** Return only bookings currently in the given status. */
    public List<Reservation> filterByStatus(String status) {
        List<Reservation> out = new ArrayList<>();
        for (Reservation r : history) {
            if (status.equals(r.getStatus())) out.add(r);
        }
        return out;
    }

    /** Generate a full booking report with a revenue summary. */
    public void generateReport() {
        System.out.println("================= BOOKING REPORT =================");
        if (history.isEmpty()) {
            System.out.println("No bookings recorded.");
        } else {
            double revenue = 0.0;
            int active = 0;
            for (Reservation r : history) {
                System.out.println("  " + r);
                if ("CONFIRMED".equals(r.getStatus())) {
                    revenue += r.getTotalCost();
                    active++;
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Total bookings recorded : %d%n", history.size());
            System.out.printf("Active (confirmed)      : %d%n", active);
            System.out.printf("Revenue (confirmed only): Rs.%.2f%n", revenue);
        }
        System.out.println("==================================================");
    }
}