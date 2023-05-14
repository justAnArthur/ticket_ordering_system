package fiit.oop.oop_ticket_ordering_system.services.chain.reservation.processes;

import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Itinerary;
import fiit.oop.oop_ticket_ordering_system.services.chain.AbstractProcessElement;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationServiceImpl;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Process for handling notification about reservation.
 */
@Component
public class NotificationReservationProcess extends AbstractProcessElement<ReserveItineraryRequest, Itinerary> {


    private final NotificationServiceImpl notificationServiceImpl;

    @Autowired
    public NotificationReservationProcess(
            NotificationServiceImpl notificationServiceImpl
    ) {
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @Override
    public Itinerary handler(ReserveItineraryRequest request, Itinerary object) {
        object.getReservations().forEach(flightReservation -> {
            flightReservation.getPassengers().forEach(passenger -> {
                notificationServiceImpl.handle(passenger, NotificationType.CreatedReservation);
            });
        });

        return this.callNext(request, object);
    }
}
