package fiit.oop.oop_ticket_ordering_system.services.chain;

import lombok.Setter;

import java.util.function.BiFunction;

/**
 * Abstract class for process element.
 *
 * @param <R> handler request
 * @param <T> object for changing
 */
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
