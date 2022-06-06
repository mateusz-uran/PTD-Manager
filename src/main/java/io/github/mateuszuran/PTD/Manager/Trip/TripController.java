package io.github.mateuszuran.PTD.Manager.Trip;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardService;
import io.github.mateuszuran.PTD.Manager.Counters.CounterService;
import io.github.mateuszuran.PTD.Manager.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home/card")
@Controller
public class TripController {
    private final CardService cardService;
    private final TripService tripService;
    private final CounterService counterService;

    public TripController(final CardService cardService, final TripService tripService, final CounterService counterService) {
        this.cardService = cardService;
        this.tripService = tripService;
        this.counterService = counterService;
    }

    @GetMapping("/add-trip")
    public String addTrip(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        if(cardService.checkIfCardIsDone(userDetails.getUserId())) {
            return "redirect:/home/card/" + cardId.getId() + "/?false";
        } else {
        model.addAttribute("trip", new Trip());
        return "trip_form";
        }
    }

    @PostMapping("/save-trip")
    public String saveTrip(Trip trip, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        trip.setCard(cardId);
        trip.setCarMileage(trip.subtract());
        tripService.saveTrip(trip);
        counterService.toggleToFalse(cardId.getId());
        return "redirect:/home/card/" + cardId.getId();
    }

    @GetMapping("/trip/edit/{id}")
    public String showEditTripForm(@PathVariable("id") Integer id, Model model) {
        Trip trip = tripService.findTripById(id);
        model.addAttribute("trip", trip);
        return "trip_form";
    }

    @GetMapping("/trip/delete/{id}")
    public String deleteTrip(@PathVariable("id") Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        tripService.deleteTrip(id);
        counterService.toggleToFalse(cardId.getId());
        return "redirect:/home/card/" + cardId.getId();
    }
}
