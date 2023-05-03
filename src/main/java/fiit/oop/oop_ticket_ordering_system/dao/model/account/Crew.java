package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Flight;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "crew")
public class Crew extends Person {
}
