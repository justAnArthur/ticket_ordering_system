package fiit.oop.oop_ticket_ordering_system.dao.repositories;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FlightInstanceRepository extends CrudRepository<FlightInstance, Long>, JpaRepository<FlightInstance, Long> {
    @Query("select i from FlightInstance i where i.status = 1")
    Collection<FlightInstance> findAllScheduled();
}
