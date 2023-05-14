package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "passenger")
@DiscriminatorValue("passenger")
public class Passenger extends Person {
}


