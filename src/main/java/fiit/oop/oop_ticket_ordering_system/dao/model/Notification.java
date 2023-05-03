package fiit.oop.oop_ticket_ordering_system.dao.model;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private NotificationType type;

    @CreatedDate
    private Date createdDate;

    @ManyToOne
    private Person person;
}
