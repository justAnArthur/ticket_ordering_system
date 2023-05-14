package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatSort;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatType;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * This class will represent all seats of an aircraft assigned to a specific flight instance.
 * All reservations of this flight instance will assign seats to passengers through this class.
 */
@Getter
@Entity
@Table(name = "flight_seat")
public class FlightSeat/* extends Seat*/ {

    private final String reservationNumber;
    private final float fare;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String seatNumber;
    private SeatType type;
    private SeatSort sort;

    public FlightSeat() {
        fare = 0.0f;
        reservationNumber = null;
    }

    /**
     * @param fare              fare of the seat, which is calculated based on the seat type and sort
     * @param reservationNumber reservation number of the seat
     */
    public FlightSeat(float fare, String reservationNumber) {
        this.fare = fare;
        this.reservationNumber = reservationNumber;
    }
}
