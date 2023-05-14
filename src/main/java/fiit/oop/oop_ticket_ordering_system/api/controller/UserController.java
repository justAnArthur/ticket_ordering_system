package fiit.oop.oop_ticket_ordering_system.api.controller;

import fiit.oop.oop_ticket_ordering_system.api.req.FindItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.api.req.ReserveItineraryRequest;
import fiit.oop.oop_ticket_ordering_system.api.res.FindItineraryResponse;
import fiit.oop.oop_ticket_ordering_system.dao.model.flight.FlightInstance;
import fiit.oop.oop_ticket_ordering_system.dao.repositories.*;
import fiit.oop.oop_ticket_ordering_system.services.flight.FlightService;
import fiit.oop.oop_ticket_ordering_system.utils.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    AirportRepository airportRepository;
    FlightRepository flightRepository;
    FlightInstanceRepository flightInstanceRepository;
    FlightReservationRepository flightReservationRepository;
    ItineraryRepository itineraryRepository;
    PassengerRepository passengerRepository;

    FlightService flightService;
    DatesUtils dates;

    @Autowired
    public UserController(
            AirportRepository airportRepository,
            FlightRepository flightRepository,
            FlightInstanceRepository flightInstanceRepository,
            FlightReservationRepository flightReservationRepository,
            ItineraryRepository itineraryRepository,
            PassengerRepository passengerRepository,

            FlightService flightService,
            DatesUtils dates
    ) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.flightReservationRepository = flightReservationRepository;
        this.itineraryRepository = itineraryRepository;
        this.passengerRepository = passengerRepository;
        this.flightService = flightService;
        this.dates = dates;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("request", new FindItineraryRequest());
        model.addAttribute("availableAirports", airportRepository.findAll());

        model.addAttribute("response", new FindItineraryResponse(
                flightInstanceRepository.findAllScheduled().stream()
                        .map(List::of)
                        .toList()
        ));

        return "user/index";
    }


    @PostMapping
    public String find(Model model, @ModelAttribute("request") FindItineraryRequest request) {
        model.addAttribute("request", request);
        model.addAttribute("availableAirports", airportRepository.findAll());

        model.addAttribute("response", new FindItineraryResponse(flightService.findRoutes(request.getDateFrom(), request.getAirportFrom(), request.getAirportTo())));

        return "user/index";
    }

    @GetMapping(value = "/reserve")
    public String showReserve(Model model, @RequestParam("ids") String ids) {
        if (ids.isBlank()) {
            model.addAttribute("errorMessage", "params is wrong");
            return "user/reserve/index";
        }

        long[] longIds = Arrays.stream(ids.split(",")).mapToLong(Long::valueOf).toArray();

        List<FlightInstance> route = flightService.getRoute(longIds);

        if (route != null)
            model.addAttribute("instances", route);
        else
            model.addAttribute("errorMessage", "not available");

        return "user/reserve/index";
    }


    @PostMapping(value = "/reserve")
    public String reserve(Model model, @RequestParam LinkedMultiValueMap<String, String> formData) {
        try {
            if (flightService.reserve(ReserveItineraryRequest.fromFormData(formData)))
                model.addAttribute("result", "ok");
            else
                model.addAttribute("errorMessage", "unable to create reservation");

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            model.addAttribute("errorMessage", "field error");
        }

        return "user/reserve/thanks";
    }
/*
    @GetMapping(value = "/test")
    public String test(Model model) throws Exception {

        emailServiceImpl.sendSimpleMessage("xkozubov@stuba.sk", "Hello", "hello");

        return "user/reserve/thanks";
    }*/
}
