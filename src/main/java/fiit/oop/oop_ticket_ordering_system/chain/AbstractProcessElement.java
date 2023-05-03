package fiit.oop.oop_ticket_ordering_system.chain;

import fiit.oop.oop_ticket_ordering_system.api.req.Request;
import lombok.Setter;

import java.util.function.BiFunction;

@Setter
public abstract class AbstractProcessElement<R, T> implements ProcessElement<R, T> {

    private BiFunction<R, T, T> next;

    protected T callNext(R request, T object) {
        if (next != null) {
            return next.apply(request, object);
        }
        return object;
    }
}
