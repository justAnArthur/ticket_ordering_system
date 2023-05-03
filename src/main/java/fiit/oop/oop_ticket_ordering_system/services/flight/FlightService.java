package fiit.oop.oop_ticket_ordering_system.services.flight;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Airport;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<List<FlightInstance>> findRoutes(LocalDate date, Airport departure, Airport arrival);

    List<FlightInstance> getRoute(long[] ids);

    boolean reserve(ReserveItineraryRequest request);
}
