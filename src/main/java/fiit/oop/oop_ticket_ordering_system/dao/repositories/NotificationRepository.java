package fiit.oop.oop_ticket_ordering_system.dao.repositories;

import fiit.oop.oop_ticket_ordering_system.dao.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long>, JpaRepository<Notification, Long> {
}
