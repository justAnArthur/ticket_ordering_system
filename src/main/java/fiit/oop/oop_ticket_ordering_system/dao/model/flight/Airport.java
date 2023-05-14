package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.Address;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Each airline operates out of different airports. Each airport has a name, address, and a unique code.
 */
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

    /**
     * @param name    name of the airport
     * @param code    unique code of the airport
     * @param address address of the airport
     */
    public Airport(String name, String code, Address address) {
        this.name = name;
        this.code = code;
        this.address = address;
    }
}
