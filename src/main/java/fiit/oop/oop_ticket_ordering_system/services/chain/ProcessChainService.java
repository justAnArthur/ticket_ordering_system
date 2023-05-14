package fiit.oop.oop_ticket_ordering_system.services.chain;

public interface ProcessChainService<R, T> {
    T evalute(R request, T object);
}
