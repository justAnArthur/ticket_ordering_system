package fiit.oop.oop_ticket_ordering_system.services.notification;

import fiit.oop.oop_ticket_ordering_system.dao.model.Notification;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.NotificationRepository;
import fiit.oop.oop_ticket_ordering_system.services.notification.listeners.EmailNotificationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.Vector;

@Service
public class NotificationServiceImpl {

    private final Vector<NotificationListener> listeners = new Vector<NotificationListener>();

    private final NotificationRepository notificationRepository;

    @Autowired
    protected NotificationServiceImpl(
            NotificationRepository notificationRepository,

            EmailNotificationListener emailNotificationListener
    ) {
        this.notificationRepository = notificationRepository;

        this.register(emailNotificationListener);
    }

    public void register(NotificationListener listener) {
        listeners.add(listener);
    }

    public void handle(Person person, NotificationType type) {
        Notification notification = new Notification() {{
            setType(type);
            setPerson(person);
        }};

        for (Enumeration<NotificationListener> e = listeners.elements(); e.hasMoreElements(); )
            (e.nextElement()).handleSend(person, type);

//        notificationRepository.save(notification);
    }
}
