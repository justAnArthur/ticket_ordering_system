package fiit.oop.oop_ticket_ordering_system.services.notification.listeners;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import fiit.oop.oop_ticket_ordering_system.api.exception.ReservationExceptionCode;
import fiit.oop.oop_ticket_ordering_system.api.exception.TOSException;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationListener;
import fiit.oop.oop_ticket_ordering_system.services.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Objects;


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
        if (Objects.requireNonNull(type) == NotificationType.CreatedReservation) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@oop-ticket-ordering-system.com");
            message.setTo(person.getEmail());
            message.setSubject("Reservation was created. Thank You.");
            message.setText("Hello )");

            Mustache.Compiler compiler = Mustache.compiler();
            Template mustache = compiler.compile("user/email/createdReservation");
            StringWriter writer = new StringWriter();
            mustache.execute(
                    new HashMap<String, Object>() {{
                        put("itinerary", person.getName());
                    }},
                    writer
            );

            try {
                emailSender.send(message);
            } catch (Exception ex) {
                throw new TOSException(ReservationExceptionCode.ERROR_WHILE_SENDING_A_NOTIFICATION);
            }
        }
    }
}
