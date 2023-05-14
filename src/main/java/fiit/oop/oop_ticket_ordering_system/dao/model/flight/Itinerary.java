package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An itinerary is a collection of flight reservations. It is created when a user books a flight.
 * Coz there is not be a direct flight to your destination, you may have to take multiple flights to get there.
 */
@Data
@Entity
@Table(name = "itinerary")
public final class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Airport startingAirport;
    @ManyToOne
    private Airport finalAirport;
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightReservation> reservations = new LinkedList<>();

    /**
     * @return all unique passengers in a reservation
     */
    @JsonProperty(value = "passengers", required = true)
    public Collection<Passenger> getAllPassengers() {
        return this.reservations.stream()
                .map(FlightReservation::getPassengers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
