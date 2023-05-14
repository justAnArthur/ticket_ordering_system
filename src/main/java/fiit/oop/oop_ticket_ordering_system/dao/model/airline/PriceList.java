package fiit.oop.oop_ticket_ordering_system.dao.model.airline;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * PriceList for seats fares in aircraft.
 */
@Data
@Entity
@Table(name = "price_list")
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    private Map<SeatSort, Double> prices = new HashMap<>();

    private Double defaultPrice = 0.0;
}
