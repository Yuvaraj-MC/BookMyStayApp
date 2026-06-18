package org.example.service;

import org.example.model.Reservation;

import java.util.LinkedList;
import java.util.Queue;

/**
 * USE CASE 3 — Booking Request (First-Come-First-Served).
 *
 * Wraps a FIFO Queue<Reservation> (LinkedList implementation) so that booking
 * requests are processed strictly in arrival order — fair allocation during
 * peak demand, with no race conditions at the request level.
 */
public class BookingQueueService {

    private final Queue<Reservation> bookingQueue = new LinkedList<>();

    /** Accept a booking request and place it at the back of the queue. */
    public void enqueue(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.printf("Queued  -> %s (%s, %s)%n",
                reservation.getReservationId(),
                reservation.getGuestName(),
                reservation.getRoomType());
    }

    /** Remove and return the next request in arrival order, or null if empty. */
    public Reservation dequeue() {
        return bookingQueue.poll();
    }

    /** Look at the next request without removing it. */
    public Reservation peek() {
        return bookingQueue.peek();
    }

    public boolean isEmpty() {
        return bookingQueue.isEmpty();
    }

    public int size() {
        return bookingQueue.size();
    }
}