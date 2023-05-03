package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "reservation")
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationNumber;

    @ManyToOne
    private FlightInstance flight;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Passenger, FlightSeat> seatMap;

    private ReservationStatus status;

    public Collection<Passenger> getPassengers() {
        return seatMap.keySet();
    }

    public Collection<FlightSeat> getSeats() {
        return seatMap.values();
    }
}
