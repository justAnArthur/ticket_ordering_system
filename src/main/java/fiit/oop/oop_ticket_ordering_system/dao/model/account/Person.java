package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import fiit.oop.oop_ticket_ordering_system.dao.model.Address;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

    @Id
    private String email;
    private String phone;
    private String name;

    private Address address;

    public Person() {
    }

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
