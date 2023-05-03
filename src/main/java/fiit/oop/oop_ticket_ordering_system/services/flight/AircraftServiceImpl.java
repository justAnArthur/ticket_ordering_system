package fiit.oop.oop_ticket_ordering_system.services.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Seat;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatSort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AircraftServiceImpl implements AircraftService {

    public ArrayList<Seat> generateSeats() {
        ArrayList<Seat> seats = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            seats.add(new Seat(SeatSort.FirstClass));
        }

        for (int i = 0; i < 36; i++) {
            seats.add(new Seat(SeatSort.Economy));
        }

        return seats;
    }
}
