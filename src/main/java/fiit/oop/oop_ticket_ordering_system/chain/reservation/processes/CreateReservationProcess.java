package fiit.oop.oop_ticket_ordering_system.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.chain.AbstractProcessElement;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightReservation;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.ReservationStatus;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightInstanceRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.ItineraryRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.PassengerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class CreateReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {

    private final FlightInstanceRepository flightInstanceRepository;
    private final PassengerRepository passengerRepository;
    private final ItineraryRepository itineraryRepository;

    private final SettingSeatMapProcess settingSeatMapProcess;

    @Autowired
    CreateReservationProcess(
            FlightInstanceRepository flightInstanceRepository,
            PassengerRepository passengerRepository,
            ItineraryRepository itineraryRepository,

            SettingSeatMapProcess settingSeatMapProcess
    ) {
        this.flightInstanceRepository = flightInstanceRepository;
        this.passengerRepository = passengerRepository;
        this.itineraryRepository = itineraryRepository;

        this.settingSeatMapProcess = settingSeatMapProcess;
    }

    @SneakyThrows
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

//        itineraryRepository.save(itinerary);

        return this.callNext(request, itinerary);
    }
}
