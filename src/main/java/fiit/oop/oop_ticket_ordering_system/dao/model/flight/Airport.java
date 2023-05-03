package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.Address;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String code;
    private Address address;

    public Airport() {

    }

    public Airport(String name, String code, Address address) {
        this.name = name;
        this.code = code;
        this.address = address;
    }
}
