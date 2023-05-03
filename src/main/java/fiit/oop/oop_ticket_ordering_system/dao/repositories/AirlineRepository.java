package fiit.oop.oop_ticket_ordering_system.dao.repositories;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends CrudRepository<Airline, Long>, JpaRepository<Airline, Long> {
}
