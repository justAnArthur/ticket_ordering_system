package fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightReservation;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.ReservationStatus;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightInstanceRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightReservationRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.ItineraryRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.PassengerRepository;
import fiit.oop.oop_ticket_ordering_system.services.chain.AbstractProcessElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Process for creating reservation from request and saving it to database.
 */
@Component
public class CreateReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightReservationRepository flightReservationRepository;
    private final PassengerRepository passengerRepository;
    private final ItineraryRepository itineraryRepository;

    private final SettingSeatMapProcess settingSeatMapProcess;

    @Autowired
    CreateReservationProcess(
            FlightInstanceRepository flightInstanceRepository,
            FlightReservationRepository flightReservationRepository, PassengerRepository passengerRepository,
            ItineraryRepository itineraryRepository,

            SettingSeatMapProcess settingSeatMapProcess
    ) {
        this.flightInstanceRepository = flightInstanceRepository;
        this.flightReservationRepository = flightReservationRepository;
        this.passengerRepository = passengerRepository;
        this.itineraryRepository = itineraryRepository;

        this.settingSeatMapProcess = settingSeatMapProcess;
    }

    /**
     * Method for creating reservation from request and saving it to database.
     *
     * @param request   request with reservation data
     * @param itinerary itinerary to which reservation will be added
     * @return itinerary with added required reservation data
     */
    @Override
    public Itinerary handler(ReserveItineraryRequest request, Itinerary itinerary) {

        Set<Long> ids = new HashSet<>(request.getReservation().keySet());

        List<FlightInstance> instances = ids
                .stream()
                .map(flightInstanceRepository::findById)
                .map(Optional::orElseThrow)
                .toList();

        for (FlightInstance instance : instances) {
            FlightReservation reservation = new FlightReservation();
            reservation.setFlight(instance);

            Passenger[] passengersForCurrentInstance = request.getReservation().get(instance.getId());
            passengerRepository.saveAll(Arrays.asList(passengersForCurrentInstance));

            settingSeatMapProcess.handler(passengersForCurrentInstance, reservation);

            reservation.setStatus(ReservationStatus.CheckedIn);
            itinerary.getReservations().add(reservation);
        }

        itinerary.setCreationDate(LocalDateTime.now());
        itinerary.setStartingAirport(instances.get(0).getFlight().getDeparture());
        itinerary.setFinalAirport(instances.get(instances.size() - 1).getFlight().getArrival());

        Collection<FlightReservation> reservations = flightReservationRepository.saveAll(itinerary.getReservations());

        itinerary.setReservations(new LinkedList<>(reservations));
        itineraryRepository.save(itinerary);

        return this.callNext(request, itinerary);
    }
}
