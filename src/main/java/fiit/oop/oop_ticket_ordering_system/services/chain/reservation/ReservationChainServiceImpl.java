package fiit.oop.oop_ticket_ordering_system.services.chain.reservation;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.services.chain.AbstractProcessElement;
import fiit.oop.oop_ticket_ordering_system.services.chain.ProcessChainService;
import fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes.CreateReservationProcess;
import fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes.NotificationReservationProcess;
import fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes.PaymentReservationProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A main chain for handling reservation that use a Chain Design Pattern. It is composed of multiple processes.
 */
@Service
public class ReservationChainServiceImpl implements ProcessChainService<ReserveItineraryRequest, Itinerary> {

    private final List<AbstractProcessElement<ReserveItineraryRequest, Itinerary>> chain = new ArrayList<>();

    @Autowired
    public ReservationChainServiceImpl(
            CreateReservationProcess createReservationProcess,
            PaymentReservationProcess paymentReservationProcess,
            NotificationReservationProcess notificationReservationProcess
    ) {
        // Order of rules if !Important
        this.chain.add(createReservationProcess);
        this.chain.add(notificationReservationProcess);

        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1)::handler);
        }
    }

    @Override
    public Itinerary evalute(ReserveItineraryRequest request, Itinerary object) {
        return chain
                .stream()
                .findFirst()
                .map(rule -> rule.handler(request, object))
                .orElse(object);
    }
}
