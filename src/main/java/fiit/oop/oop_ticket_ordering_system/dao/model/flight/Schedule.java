package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    DayOfWeek dayOfWeek;
    LocalTime departureTime;
    LocalTime arrivalTime;
}
