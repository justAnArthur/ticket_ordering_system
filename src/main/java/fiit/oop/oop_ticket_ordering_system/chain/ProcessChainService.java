package fiit.oop.oop_ticket_ordering_system.chain;

import fiit.oop.oop_ticket_ordering_system.api.req.Request;

public interface ProcessChainService<R, T> {
    T evalute(R request, T object);
}
