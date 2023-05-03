package fiit.oop.oop_ticket_ordering_system.api.req;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Airport;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FindItineraryRequest {

    LocalDate dateFrom;
    Airport airportFrom;
    Airport airportTo;

}
