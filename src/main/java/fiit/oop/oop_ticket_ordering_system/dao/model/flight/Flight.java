package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

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

    @OneToMany
    private List<FlightInstance> instances;

    @NonNull
    private Schedule schedule;

    public Flight() {

    }

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
