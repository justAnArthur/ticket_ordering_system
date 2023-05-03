package fiit.oop.oop_ticket_ordering_system.chain;

import fiit.oop.oop_ticket_ordering_system.api.req.Request;

public interface ProcessElement<R, T> {
    T handler(R request, T object);
}
