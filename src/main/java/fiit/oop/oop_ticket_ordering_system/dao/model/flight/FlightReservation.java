package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A reservation is made against a flight instance and has attributes like a unique reservation number,
 * list of passengers and their assigned seats, reservation status, etc.
 */
@Data
@Entity
@Table(name = "reservation")
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationNumber;

    @ManyToOne
    private FlightInstance flight;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Passenger, FlightSeat> seatMap;

    private ReservationStatus status;

    public Set<Map.Entry<Passenger, FlightSeat>> getSeatEntrySet() {
        return seatMap.entrySet();
    }

    /**
     * @return all unique passengers in a reservation
     */
    public Collection<Passenger> getPassengers() {
        return seatMap.keySet();
    }

    public Collection<FlightSeat> getSeats() {
        return seatMap.values();
    }
}
