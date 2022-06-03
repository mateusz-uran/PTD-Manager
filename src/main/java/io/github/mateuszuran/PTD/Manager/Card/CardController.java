package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.Counters.CounterService;
import io.github.mateuszuran.PTD.Manager.Counters.Counters;
import io.github.mateuszuran.PTD.Manager.Fuel.Fuel;
import io.github.mateuszuran.PTD.Manager.Fuel.FuelService;
import io.github.mateuszuran.PTD.Manager.Security.CustomUserDetails;
import io.github.mateuszuran.PTD.Manager.Trip.Trip;
import io.github.mateuszuran.PTD.Manager.Trip.TripService;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/home")
@Controller
public class CardController {
    private final CardService cardService;
    private final UserService userService;
    private final TripService tripService;
    private final FuelService fuelService;
    private final CounterService counterService;

    public CardController(final CardService cardService, final UserService userService,
                          final TripService tripService, final FuelService fuelService,
                          final CounterService counterService) {
        this.cardService = cardService;
        this.userService = userService;
        this.tripService = tripService;
        this.fuelService = fuelService;
        this.counterService = counterService;
    }

    @GetMapping("/add-card")
    public String showCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "card_form";
    }

    @PostMapping("/save-card")
    public String saveNewCard(Card card, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserById(userDetails.getUserId());
        if(cardService.checkIfCardExists(card)) {
            return "redirect:/home/add-card/?false";
        }
        card.setUser(user);
        card.setAuthorFullName(user.fullName());
        card.setCreateDate(cardService.currentDate());
        card.setDone(false);
        cardService.saveCard(card);
        counterService.saveEmptyCounters(card);

        return "redirect:/home/add-card/?success";
    }

    @GetMapping("/card/{id}")
    public String showCard(@PathVariable("id") Integer id, Model model) {
        List<Trip> listTrips = tripService.findAllAndSort(id);
        model.addAttribute("listTrips", listTrips);
        List<Fuel> listFuels = fuelService.findAllAndSort(id);
        model.addAttribute("listFuels", listFuels);
        Counters counters = counterService.findByCardId(id);
        model.addAttribute("counters", counters);
        Card card = cardService.findCard(id);
        model.addAttribute("card", card);

        return "card";
    }

    @GetMapping("/card/delete/{id}")
    public String deleteCard(@PathVariable("id") Integer id) {
        cardService.deleteCard(id);
        return "redirect:/home";
    }

    @GetMapping("/card/toggle/{id}")
    public String toggleCard(@PathVariable("id") Integer id) {
        cardService.toggleCard(id);
        return "redirect:/home/card/" + id;
    }
}
