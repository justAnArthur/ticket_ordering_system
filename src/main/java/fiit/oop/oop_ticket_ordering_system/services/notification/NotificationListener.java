package fiit.oop.oop_ticket_ordering_system.services.notification;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;

public interface NotificationListener {
    void handleSend(Person person, NotificationType type);
}
