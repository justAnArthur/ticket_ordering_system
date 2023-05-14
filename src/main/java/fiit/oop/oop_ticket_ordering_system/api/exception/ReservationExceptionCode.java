package fiit.oop.oop_ticket_ordering_system.api.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReservationExceptionCode implements ExceptionCode {
    NO_ACCESSIBLE_SEAT("0001", "No seats available for this flight"),
    FLIGHT_IS_UNDEFINED("0002", "Cannot manage with flight due to is undefined"),

    ERROR_WHILE_SENDING_A_NOTIFICATION("0010", "There is an error while sending a notification to participant"),

    ;

    private final String value;
    private final String message;

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
