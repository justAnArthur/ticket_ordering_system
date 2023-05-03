package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "itinerary")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Airport startingAirport;
    @ManyToOne
    private Airport finalAirport;
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "reservationNumber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightReservation> reservations = new LinkedList<>();

    // todo crate status to get if all success
}
