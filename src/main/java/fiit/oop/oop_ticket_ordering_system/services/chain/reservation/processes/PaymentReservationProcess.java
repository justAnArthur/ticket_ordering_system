package fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.services.chain.AbstractProcessElement;
import org.springframework.stereotype.Component;

/**
 * Process for handling payment of reservation.
 */
@Component
public class PaymentReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {

    @Override
    public Itinerary handler(ReserveItineraryRequest request, Itinerary object) {
        return this.callNext(request, object);
    }
}
