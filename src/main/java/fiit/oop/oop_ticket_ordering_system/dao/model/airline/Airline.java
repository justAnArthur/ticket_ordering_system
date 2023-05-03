package fiit.oop.oop_ticket_ordering_system.dao.model.airline;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "airline")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String code;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Aircraft> aircrafts;

    @OneToOne(cascade = CascadeType.ALL)
    private PriceList priceList;
}
