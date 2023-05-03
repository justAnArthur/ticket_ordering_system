package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "passenger")
public class Passenger extends Person {
}


