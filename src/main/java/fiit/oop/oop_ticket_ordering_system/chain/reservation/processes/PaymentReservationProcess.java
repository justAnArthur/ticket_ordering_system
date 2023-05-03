package fiit.oop.oop_ticket_ordering_system.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.chain.AbstractProcessElement;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import org.springframework.stereotype.Component;

@Component
public class PaymentReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {

    @Override
    public Itinerary handler(ReserveItineraryRequest request, Itinerary object) {


        return this.callNext(request, object);
    }
}
