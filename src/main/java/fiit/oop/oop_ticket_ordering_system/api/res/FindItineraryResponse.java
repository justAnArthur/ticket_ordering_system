package fiit.oop.oop_ticket_ordering_system.api.res;

import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class FindItineraryResponse {
    List<FindItineraryWithLink> routes;

    public FindItineraryResponse(List<List<FlightInstance>> routes) {
        this.routes = routes.stream()
                .map(route ->
                        new FindItineraryWithLink(
                                route,
                                route.stream()
                                        .map(FlightInstance::getId)
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(","))
                        )
                )
                .toList();
    }

    @AllArgsConstructor
    class FindItineraryWithLink {
        List<FlightInstance> route;
        // Will be used in url parameters to specify which flightInstances are described in selected route
        String ids;
    }
}
