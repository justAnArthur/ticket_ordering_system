package fiit.oop.oop_ticket_ordering_system.dao.model.flight;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Crew;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Customer;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Pilot;

import java.util.ArrayList;

public class Hands extends ArrayList<Person> {
    @Override
    public boolean add(Person person) {
        if (person instanceof Pilot || person instanceof Crew || person instanceof Customer) {
            return super.add(person);
        }
        return false;
    }
}