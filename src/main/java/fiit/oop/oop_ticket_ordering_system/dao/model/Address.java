package fiit.oop.oop_ticket_ordering_system.dao.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@RequiredArgsConstructor
@MappedSuperclass
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
