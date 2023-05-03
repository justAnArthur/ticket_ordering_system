package fiit.oop.oop_ticket_ordering_system.dao.model.airline;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String modal;
    private int manufacturingYear;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    public Aircraft() {

    }

    public Aircraft(String name, String modal, int manufacturingYear, List<Seat> seats) {
        this.name = name;
        this.modal = modal;
        this.manufacturingYear = manufacturingYear;
        this.seats = seats;
    }
}
