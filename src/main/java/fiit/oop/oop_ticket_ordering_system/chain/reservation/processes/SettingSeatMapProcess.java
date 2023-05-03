package fiit.oop.oop_ticket_ordering_system.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.chain.AbstractProcessElement;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Seat;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightReservation;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightSeat;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightReservationRepository;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Component
public class SettingSeatMapProcess extends AbstractProcessElement<Passenger[], FlightReservation> {

    private final FlightReservationRepository flightReservationRepository;

    @Autowired
    public SettingSeatMapProcess(FlightReservationRepository flightReservationRepository) {
        this.flightReservationRepository = flightReservationRepository;
    }

    /**
     * @param object the field `flight` must be set
     */
    @SneakyThrows
    @Override
    public FlightReservation handler(Passenger[] request, FlightReservation object) {
        if (object.getFlight() == null)
            throw new Exception("unable te set seatMap for reservation without flight instance.");

        List<FlightSeat> nonAvailableSeats = flightReservationRepository
                .findAllByFlight(object.getFlight())
                .stream()
                .map(FlightReservation::getSeats)
                .flatMap(Collection::stream)
                .toList();

        List<Seat> availableSeats = object.getFlight().getAircraft().getSeats()
                .stream()
                .filter(seat -> nonAvailableSeats
                        .stream()
                        .noneMatch(_seat -> _seat.getSeatNumber().equals(seat.getSeatNumber()))
                )
                .toList();

        if (availableSeats.size() < request.length)
            throw new Exception("there are no accessible seats");

        object.setSeatMap(new HashMap<>() {{
            for (int i = 0; i < request.length; i++) {
                FlightSeat flightSeat = new FlightSeat(100, "todo");
                BeanUtils.copyProperties(flightSeat, availableSeats.get(i), "id");
                put(request[i], flightSeat);
            }
        }});


        return this.callNext(request, object);
    }
}
