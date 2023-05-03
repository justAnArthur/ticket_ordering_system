package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "admin")
public class Admin extends Person {

    public Admin() {
    }

    public Admin(String name, String email, String phone) {
        super(name, email, phone);
    }

}
