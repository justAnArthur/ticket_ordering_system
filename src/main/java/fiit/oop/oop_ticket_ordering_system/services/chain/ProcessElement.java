package fiit.oop.oop_ticket_ordering_system.services.chain;

public interface ProcessElement<R, T> {
    T handler(R request, T object);
}
