package fiit.oop.oop_ticket_ordering_system.api.controller;

import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Aircraft;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.Airline;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.PriceList;
import fiit.oop.oop_ticket_ordering_system.dao.model.airline.SeatSort;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.*;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.*;
import fiit.oop.oop_ticket_ordering_system.services.flight.AircraftService;
import fiit.oop.oop_ticket_ordering_system.services.flight.FlightService;
import fiit.oop.oop_ticket_ordering_system.utils.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    AirportRepository airportRepository;
    AirlineRepository airlineRepository;
    AircraftRepository aircraftRepository;
    FlightRepository flightRepository;
    FlightInstanceRepository flightInstanceRepository;
    ItineraryRepository itineraryRepository;

    FlightService flightService;
    AircraftService aircraftService;

    DatesUtils dates;

    @Autowired
    public AdminController(
            FlightService flightService,
            AircraftService aircraftService,
            DatesUtils dates,

            AirportRepository airportRepository,
            FlightRepository flightRepository,
            FlightInstanceRepository flightInstanceRepository,
            AirlineRepository airlineRepository,
            AircraftRepository aircraftRepository,
            ItineraryRepository itineraryRepository
    ) {
        this.flightService = flightService;
        this.aircraftService = aircraftService;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.airlineRepository = airlineRepository;
        this.aircraftRepository = aircraftRepository;
        this.itineraryRepository = itineraryRepository;
        this.dates = dates;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airportsCount", airportRepository.count());
        model.addAttribute("flightsCount", flightRepository.count());
        model.addAttribute("airlinesCount", airlineRepository.count());

        return "admin/index";
    }


    @GetMapping("/airport")
    public String airport(Model model) {

        model.addAttribute("airports", airportRepository.findAll());

        return "admin/airport/index";
    }

    @GetMapping(value = {"/airport/add"})
    public String showAddAirport(Model model) {
        Airport airport = new Airport();

        model.addAttribute("add", true);
        model.addAttribute("airport", airport);

        return "admin/airport/add";
    }

    @PostMapping(value = "/airport/add")
    public String addAirport(Model model, @ModelAttribute("airport") Airport airport) {
        try {
            airportRepository.save(airport);

            return "redirect:/admin/airport";
//            return "redirect:/airport" + String.valueOf(newUser.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
//            logger.error(errorMessage); todo
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "admin/airport/add";
        }
    }

    @GetMapping(value = {"/airport/{id}/edit"})
    public String showEditAirport(Model model, @PathVariable long id) {
        Airport airport = null;
        try {
            airport = airportRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("airport", airport);
        return "admin/airport/add";
    }

    @PostMapping(value = {"/airport/{id}/edit"})
    public String updateAirport(Model model, @PathVariable long id, @ModelAttribute("airport") Airport airport) {
        try {
            airport.setId(id);
            airportRepository.save(airport);
            return "redirect:/admin/airport";
//            return "redirect:/admin/airport/" + String.valueOf(airport.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);

            return "admin/airport/add";
        }
    }

    @PostMapping(value = {"/airport/{id}/delete"})
    public String deleteAirportById(Model model, @PathVariable long id) {
        try {
            airportRepository.deleteById(id);
            return "redirect:/admin/airport";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            return "admin/airport/index";
        }
    }

    /* ------------------------------------------------------------------ */

    @GetMapping("/flight")
    public String flights(Model model) {

        model.addAttribute("flights", flightRepository.findAll());

        return "admin/flight/index";
    }

    @GetMapping(value = {"/flight/add"})
    public String showAddFlight(Model model) {
        Flight flight = new Flight();
        flight.setSchedule(new Schedule());

        model.addAttribute("availableDaysOfWeek", Arrays.stream(DayOfWeek.values()).map(DayOfWeek::toString).collect(Collectors.toSet()));

        model.addAttribute("add", true);
        model.addAttribute("flight", flight);
        model.addAttribute("airports", airportRepository.findAll());

        return "admin/flight/add";
    }

    @PostMapping(value = "/flight/add")
    public String addFlight(Model model, @ModelAttribute("flight") Flight flight) {
        try {
            flightRepository.save(flight);

            return "redirect:/admin/flight";
//            return "redirect:/flight" + String.valueOf(newUser.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
//            logger.error(errorMessage); todo
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "admin/flight/add";
        }
    }


    @GetMapping(value = {"/flight/{id}/edit"})
    public String showEditFlight(Model model, @PathVariable long id) {
        Flight flight = null;
        try {
            flight = flightRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        model.addAttribute("availableDaysOfWeek", Arrays.stream(DayOfWeek.values()).map(DayOfWeek::toString).collect(Collectors.toSet()));

        model.addAttribute("add", false);
        model.addAttribute("flight", flight);
        model.addAttribute("airports", airportRepository.findAll());

        return "admin/flight/add";
    }

    @PostMapping(value = {"/flight/{id}/edit"})
    public String updateFlight(Model model, @PathVariable long id, @ModelAttribute("flight") Flight flight) {
        try {
            flight.setId(id);
            flightRepository.save(flight);
            return "redirect:/admin/flight";
//            return "redirect:/admin/flight/" + String.valueOf(flight.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);

            return "admin/flight/add";
        }
    }

    /* ------------------------------------------------------------------ */

    @GetMapping(value = {"/flight/{id}/details"})
    public String showDetailsFlight(Model model, @PathVariable long id) {
        Flight flight = null;
        try {
            flight = flightRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        model.addAttribute("flight", flight);
//        model.addAttribute("instances")

        return "admin/flight/details";
    }

    @GetMapping(value = {"/flight/{id}/add"})
    public String showAddFlightInstance(Model model, @PathVariable Long id) {
        Flight flight;
        try {
            flight = flightRepository.findById(id).orElseThrow();

            FlightInstance instance = new FlightInstance();

            model.addAttribute("add", true);
            model.addAttribute("flightId", id);
            model.addAttribute("instance", instance);

            List<LocalDate> availableDates = dates.getNextNDatesForDayOfWeek(10, flight.getSchedule().getDayOfWeek());

            model.addAttribute("availableDates", availableDates.stream().map(LocalDate::toString).collect(Collectors.toSet()));

            model.addAttribute("availableStatuses", FlightStatus.values());

            // todo dynamic 'available' by selected date
            List<Aircraft> aircrafts = aircraftRepository.findAll();

            model.addAttribute("availableAircrafts", aircrafts);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "admin/flight/instance/add";
    }

    @PostMapping(value = "/flight/{id}/add")
    public String addFlightInstance(Model model, @PathVariable Long id, @ModelAttribute("flightInstance") FlightInstance instance) {
        try {
            Flight flight = flightRepository.findById(id).orElseThrow();

            instance.setId(null);
            instance.setFlight(flight);

            FlightInstance saved = flightInstanceRepository.save(instance);

            flight.getInstances().add(saved);

            flightRepository.save(flight);

            return "redirect:/admin/flight/" + id + "/details";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "admin/flight/instance/add";
        }
    }

    @GetMapping(value = {"/flight/{flightId}/{id}/edit"})
    public String showEditFlightInstance(Model model, @PathVariable long flightId, @PathVariable long id) {
        FlightInstance instance;
        Flight flight;
        try {
            instance = flightInstanceRepository.findById(id).orElseThrow();
            flight = flightRepository.findById(flightId).orElseThrow();

            model.addAttribute("add", false);
            model.addAttribute("flightId", flightId);
            model.addAttribute("instance", instance);

            List<LocalDate> availableDates = dates.getNextNDatesForDayOfWeek(10, flight.getSchedule().getDayOfWeek());

            model.addAttribute("availableDates", availableDates.stream().map(LocalDate::toString).collect(Collectors.toSet()));
            model.addAttribute("availableStatuses", FlightStatus.values());

        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "admin/flight/add";
    }

    @PostMapping(value = {"/flight/{flightId}/{id}/edit"})
    public String editFlightInstance(Model model, @PathVariable long flightId, @PathVariable long id, @ModelAttribute("flight") FlightInstance instance) {
        try {
            Flight flight = flightRepository.findById(flightId).orElseThrow();

            instance.setId(id);
            instance.setFlight(flight);

            flightInstanceRepository.save(instance);

            return "redirect:/admin/flight/" + flightId + "/details";
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);

            return "admin/flight/add";
        }
    }

    /* ------------------------------------------------------------------ */

    @GetMapping("/airline")
    public String airlines(Model model) {

        model.addAttribute("airlines", airlineRepository.findAll());

        return "admin/airline/index";
    }

    @GetMapping(value = {"/airline/add"})
    public String showAddAirline(Model model) {
        Airline airline = new Airline();

        model.addAttribute("add", true);
        model.addAttribute("airline", airline);

        return "admin/airline/add";
    }

    @PostMapping(value = "/airline/add")
    public String addAirline(Model model, @ModelAttribute("airline") Airline airline) {
        try {
            airline.setPriceList(
                    new PriceList() {{
                        getPrices().put(SeatSort.Economy, 10.0);
                        getPrices().put(SeatSort.Business, 35.0);
                        getPrices().put(SeatSort.FirstClass, 75.0);
                    }}
            );
            airlineRepository.save(airline);

            return "redirect:/admin/airline";
//            return "redirect:/airline" + String.valueOf(newUser.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
//            logger.error(errorMessage); todo
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "admin/airline/add";
        }
    }

    @GetMapping(value = {"/airline/{id}/edit"})
    public String showEditAirline(Model model, @PathVariable long id) {
        Airline airline = null;
        try {
            airline = airlineRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        model.addAttribute("add", false);
        model.addAttribute("airline", airline);

        return "admin/airline/add";
    }

    @PostMapping(value = {"/airline/{id}/edit"})
    public String updateAirline(Model model, @PathVariable long id, @ModelAttribute("airline") Airline airline) {
        try {
            airline.setId(id);
            airlineRepository.save(airline);
            return "redirect:/admin/airline";
//            return "redirect:/admin/airline/" + String.valueOf(airline.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);

            return "admin/airline/add";
        }
    }

    /* ------------------------------------------------------------------ */

    @GetMapping("/airline/{id}/details")
    public String airlineDetail(Model model, @PathVariable long id) {
        Airline airline = null;
        try {
            airline = airlineRepository.findById(id).orElseThrow();

            model.addAttribute("airline", airline);

            return "admin/airline/details";
        } catch (Exception ex) {
//
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("airline", airline);

            return "admin/airline/details";
        }
    }

    /* ------------------------------------------------------------------ */

    @GetMapping("/airline/{id}/aircraft/add")
    public String showAddAircraft(Model model, @PathVariable long id) {
        Aircraft aircraft = new Aircraft();

        model.addAttribute("add", true);
        model.addAttribute("aircraft", aircraft);
        model.addAttribute("airlineId", id);

        return "admin/airline/aircraft/add";
    }

    @PostMapping(value = "/airline/{airlineId}/aircraft/{id}/add")
    public String addAircraft(Model model, @PathVariable long airlineId, @PathVariable long id, @ModelAttribute("aircraft") Aircraft aircraft) {
        try {
            Airline airline = airlineRepository.findById(airlineId).orElseThrow();

            aircraft.setId(id);

            /* todo */
            aircraft.setSeats(aircraftService.generateSeats());

            airline.getAircrafts().add(aircraft);

            Airline savedAirline = airlineRepository.save(airline);

            return "redirect:/admin/airline/" + savedAirline.getId() + "/details";
//            return "redirect:/aircraft" + String.valueOf(newUser.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
//            logger.error(errorMessage); todo
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            model.addAttribute("airlineId", airlineId);

            return "admin/airline/aircraft/add";
        }
    }

    @GetMapping(value = {"/airline/{airlineId}/aircraft/{id}/edit"})
    public String showEditAircraft(Model model, @PathVariable long airlineId, @PathVariable long id) {
        Aircraft aircraft = null;
        try {
            aircraft = aircraftRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        model.addAttribute("add", false);
        model.addAttribute("aircraft", aircraft);
        model.addAttribute("airlineId", airlineId);

        return "admin/airline/aircraft/add";
    }

    @PostMapping(value = {"/airline/{airlineId}/aircraft/{id}/edit"})
    public String updateAircraft(Model model, @PathVariable long airlineId, @PathVariable long id, @ModelAttribute("aircraft") Aircraft aircraft) {
        try {
            aircraft.setId(id);

            aircraftRepository.save(aircraft);

            return "redirect:/admin/airline/" + airlineId + "/details";
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);

            return "admin/airline/aircraft/add";
        }
    }

    /*------------------------------*/


    @GetMapping("/itinerary")
    public String showItineraries(Model model) {

        model.addAttribute("itineraries", itineraryRepository.findAll());

        return "admin/itinerary/index";
    }


    @GetMapping("/itinerary/{id}/details")
    public String showItineraryDetails(Model model, @PathVariable long id) {

        try {
            model.addAttribute("itinerary", itineraryRepository.findById(id).orElseThrow());
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "admin/itinerary/details";
    }

}
