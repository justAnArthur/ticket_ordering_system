package fiit.oop.oop_ticket_ordering_system.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TOSException extends RuntimeException {
    private Object[] args;
    private ExceptionCode code;

    public TOSException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public TOSException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }

    public TOSException(Object[] args, ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }
}

