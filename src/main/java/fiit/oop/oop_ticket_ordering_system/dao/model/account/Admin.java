package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "admin")
@DiscriminatorValue("admin")
public class Admin extends Person {
}
