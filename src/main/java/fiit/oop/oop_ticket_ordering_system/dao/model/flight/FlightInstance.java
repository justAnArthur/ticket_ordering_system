package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Crew;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Pilot;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Aircraft;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "flight_instance")
@ToString(onlyExplicitlyIncluded = true)
public class FlightInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, updatable = false)
    private Flight flight;

    @ToString.Include
    private LocalDate date;
    private String gate;
    @ToString.Include
    private FlightStatus status;

    @ManyToOne
    private Aircraft aircraft;

    @ManyToMany
    private List<Crew> crew;
    @ManyToMany
    private List<Pilot> pilots;

    @ManyToMany
    private List<FlightSeat> flightSeats;

    @JsonProperty("departureDateTime")
    public LocalDateTime getDepartureDateTime() {
        return this.getDate().atTime(this.getFlight().getSchedule().getDepartureTime());
    }

    @JsonProperty("arrivalDateTime")
    public LocalDateTime getArrivalDateTime() {
        return this.getDate().atTime(this.getFlight().getSchedule().getArrivalTime());
    }
}
