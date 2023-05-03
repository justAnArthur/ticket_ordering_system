package fiit.oop.oop_ticket_ordering_system.services.mail;

public interface EmailService {
    boolean sendSimpleMessage(String to, String subject, String text);
}
