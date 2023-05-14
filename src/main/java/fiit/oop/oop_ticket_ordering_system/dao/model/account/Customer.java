package fiit.oop.oop_ticket_ordering_system.dao.model.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Customer class represents customer account in database.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends Person {
    private String frequentFlyerNumber;
}


