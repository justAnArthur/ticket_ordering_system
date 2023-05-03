package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatSort;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatType;
import jakarta.persistence.*;
import lombok.Getter;

/*todo create a record*/
@Getter
@Entity
@Table(name = "flight_seat")
public class FlightSeat/* extends Seat*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String reservationNumber;
    private final float fare;
    private String seatNumber;
    private SeatType type;
    private SeatSort sort;

    public FlightSeat() {
        fare = 0.0f;
        reservationNumber = null;
    }

    public FlightSeat(float fare, String reservationNumber) {
        this.fare = fare;
        this.reservationNumber = reservationNumber;
    }
}
