package fiit.oop.oop_ticket_ordering_system.services.notification.listeners;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationListener;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements NotificationListener {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailNotificationListener(
            JavaMailSender emailSender
    ) {
        this.emailSender = emailSender;
    }

    @Override
    public void handleSend(Person person, NotificationType type) {
        switch (type) {
            case CreatedReservation -> {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("noreply@oop-ticket-ordering-system.com");
                message.setTo(person.getEmail());
                message.setSubject("Reservation was created. Thank You.");
                message.setText("Hello )");

                try {
                    emailSender.send(message);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            default -> {
            }
        }
    }
}
