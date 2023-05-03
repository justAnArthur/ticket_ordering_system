package fiit.oop.oop_ticket_ordering_system.dao.repositories;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long>, JpaRepository<Flight, Long> {
}
