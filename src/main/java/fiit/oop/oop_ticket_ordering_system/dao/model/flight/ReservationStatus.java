package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

/**
 * This enum represents the status of a reservation.
 */
public enum ReservationStatus {
    Requested,
    Pending,
    Confirmed,
    CheckedIn,
    Canceled,
    Abandoned,
    Closed,
}
