package io.github.mateuszuran.PTD.Manager.Trip;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardService;
import io.github.mateuszuran.PTD.Manager.Role.Role;
import io.github.mateuszuran.PTD.Manager.Security.CustomUserDetails;
import io.github.mateuszuran.PTD.Manager.User.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/home/card")
@Controller
public class TripController {
    private final CardService cardService;
    private final TripService tripService;

    public TripController(final CardService cardService, final TripService tripService) {
        this.cardService = cardService;
        this.tripService = tripService;
    }

    @GetMapping("/add")
    public String addTrip(Model model) {
        model.addAttribute("trip", new Trip());
        return "trip_form";
    }

    @PostMapping("/save")
    public String saveTrip(Trip trip, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        trip.setCard(cardId);
        tripService.saveTrip(trip);
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
        return "redirect:/home/card/" + cardId.getId();
    }
}
