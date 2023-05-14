package fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.exception.ReservationExceptionCode;
import fiit.oop.oop_ticket_ordering_system.api.exception.TOSException;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Seat;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightReservation;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightSeat;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightReservationRepository;
import fiit.oop.oop_ticket_ordering_system.services.chain.AbstractProcessElement;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Inner process for setting seat map to reservation.
 */
@Component
public class SettingSeatMapProcess extends AbstractProcessElement<Passenger[], FlightReservation> {

    private final FlightReservationRepository flightReservationRepository;

    @Autowired
    public SettingSeatMapProcess(FlightReservationRepository flightReservationRepository) {
        this.flightReservationRepository = flightReservationRepository;
    }

    /**
     * Method for setting seat map to reservation.
     *
     * @param request request with reservation data
     * @param object  reservation to which seat map will be set
     * @return reservation with set seat map
     */
    @SneakyThrows
    @Override
    public FlightReservation handler(Passenger[] request, FlightReservation object) {
        if (object.getFlight() == null)
            throw new TOSException(ReservationExceptionCode.FLIGHT_IS_UNDEFINED);

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
            throw new TOSException(ReservationExceptionCode.NO_ACCESSIBLE_SEAT);

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
