package fiit.oop.oop_ticket_ordering_system.dao.model.airline;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String seatNumber;
    private SeatType type;
    private SeatSort sort;

    public Seat() {
    }

    public Seat(SeatSort sort) {
        this.sort = sort;
    }
}

