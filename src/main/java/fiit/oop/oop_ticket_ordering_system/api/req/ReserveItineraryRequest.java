package fiit.oop.oop_ticket_ordering_system.api.req;

import fiit.oop.oop_ticket_ordering_system.dao.model.account.Passenger;
import fiit.oop.oop_ticket_ordering_system.dao.model.account.Person;
import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ReserveItineraryRequest implements Request {

    Map<Long, Passenger[]> reservation;

    public static ReserveItineraryRequest fromFormData(MultiValueMap<String, String> formData) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<Long, Passenger[]> map = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : formData.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            String[] tokens = key.split("[\\[\\]]+");
            long id = Long.parseLong(tokens[1]);
            int index = Integer.parseInt(tokens[2]);
            String fieldName = tokens[3].substring(1);

            Passenger[] passengers = map.getOrDefault(id, new Passenger[1]);

            if (passengers.length <= index)
                passengers = Arrays.copyOf(passengers, passengers.length + 1);

            if (passengers[index] == null)
                passengers[index] = new Passenger();

            Method method = Person.class.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), String.class);
            method.setAccessible(true);
            method.invoke(passengers[index], values.get(0));

            map.put(id, passengers);
        }

        return new ReserveItineraryRequest() {{
            setReservation(map);
        }};
    }
}
