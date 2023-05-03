package fiit.oop.oop_ticket_ordering_system.services.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Seat;

import java.util.ArrayList;

public interface AircraftService {
    ArrayList<Seat> generateSeats();
}
