package fiit.oop.oop_ticket_ordering_system.dao.repositories;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FlightReservationRepository extends CrudRepository<FlightReservation, Long>, JpaRepository<FlightReservation, Long> {
    Collection<FlightReservation> findAllByFlight(FlightInstance flight);
}
