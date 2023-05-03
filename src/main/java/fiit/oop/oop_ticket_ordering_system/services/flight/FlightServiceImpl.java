package fiit.oop.oop_ticket_ordering_system.services.flight;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.chain.reservation.ReservationChainServiceImpl;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Airport;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.AircraftRepository;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.FlightInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private AircraftRepository aircraftRepository;
    private final FlightInstanceRepository flightInstanceRepository;

    private final ReservationChainServiceImpl reservationChainServiceImpl;

    @Autowired
    public FlightServiceImpl(
            AircraftRepository aircraftRepository,
            FlightInstanceRepository flightInstanceRepository,

            ReservationChainServiceImpl reservationChainServiceImpl
    ) {
        this.aircraftRepository = aircraftRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.reservationChainServiceImpl = reservationChainServiceImpl;
    }

    @Override
    public List<List<FlightInstance>> findRoutes(LocalDate date, Airport departure, Airport arrival) {
        Collection<FlightInstance> allScheduled = flightInstanceRepository.findAllScheduled();
        List<List<FlightInstance>> routes = new ArrayList<>();

        Collection<FlightInstance> allMatchesDeparture = allScheduled.stream()
                .filter(instance -> {
                    return date.equals(instance.getDepartureDateTime().toLocalDate())
                            && departure.equals(instance.getFlight().getDeparture());
                })
                .collect(Collectors.toSet());

        Collection<FlightInstance> allMatchesArrival = allScheduled.stream()
                .filter(instance -> {
                    return arrival.equals(instance.getFlight().getArrival());
                })
                .collect(Collectors.toSet());

        // add all flight that already departures in selected airport
        routes.addAll(
                allMatchesDeparture.stream()
                        .filter(instance -> arrival.equals(instance.getFlight().getArrival()))
                        .map(List::of)
                        .toList()
        );

        allMatchesDeparture.forEach(instanceDeparture -> {
            Collection<FlightInstance> matchers = allMatchesArrival.stream()
                    .filter(instanceArrival ->
                            instanceDeparture.getFlight().getArrival().equals(instanceArrival.getFlight().getDeparture())
                                    && instanceDeparture.getArrivalDateTime().isBefore(instanceArrival.getDepartureDateTime())
                                    && instanceDeparture.getArrivalDateTime().plusHours(12).isAfter(instanceArrival.getDepartureDateTime())
                    )
                    .collect(Collectors.toSet());

            routes.addAll(
                    matchers.stream()
                            .map(instanceArrival -> List.of(instanceDeparture, instanceArrival))
                            .toList()
            );
        });

        return routes;
    }

    @Override
    public List<FlightInstance> getRoute(long[] ids) {
        try {
            return Arrays.stream(ids)
                    .mapToObj(flightInstanceRepository::findById)
                    .map(Optional::orElseThrow)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean reserve(ReserveItineraryRequest request) {
        try {
            reservationChainServiceImpl.evalute(request, new Itinerary());
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
