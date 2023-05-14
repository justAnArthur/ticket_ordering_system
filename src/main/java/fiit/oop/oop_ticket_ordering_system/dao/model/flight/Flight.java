package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * The main entity of the system. Each flight will have a flight number, departure and arrival airport, assigned aircraft, etc.
 */
@Data
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String flightNumber;
    @NonNull
    @ManyToOne(cascade = {CascadeType.ALL})
    private Airport departure;
    @NonNull
    @ManyToOne(cascade = {CascadeType.ALL})
    private Airport arrival;

    @OneToMany(mappedBy = "flight", cascade = {CascadeType.ALL})
    private List<FlightInstance> instances;

    @NonNull
    private Schedule schedule;

    public Flight() {

    }

    /**
     * @param flightNumber flight number
     * @param departure    departure airport
     * @param arrival      arrival airport
     * @param schedule     schedule of the flight
     */
    public Flight(
            @NonNull String flightNumber,
            @NonNull Airport departure,
            @NonNull Airport arrival,
            @NonNull Schedule schedule
    ) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.schedule = schedule;
    }
}
