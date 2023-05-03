package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data

@EqualsAndHashCode(callSuper = true)
public class Customer extends Person {
    private String frequentFlyerNumber;

    List<Itinerary> getItineraries() {
        return null;
    }
}


