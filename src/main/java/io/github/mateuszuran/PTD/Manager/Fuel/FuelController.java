package io.github.mateuszuran.PTD.Manager.Fuel;

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
public class FuelController {
    private final FuelService fuelService;
    private final CardService cardService;
    private final CounterService counterService;

    public FuelController(final FuelService fuelService, final CardService cardService, final CounterService counterService) {
        this.fuelService = fuelService;
        this.cardService = cardService;
        this.counterService = counterService;
    }

    @GetMapping("/add-fuel")
    public String addFuel(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        if(cardService.checkIfCardIsDone(userDetails.getUserId())) {
            return "redirect:/home/card/" + cardId.getId() + "/?false";
        } else {
        model.addAttribute("fuel", new Fuel());
        return "fuel_form";
        }
    }

    @PostMapping("/save-fuel")
    public String saveFuel(Fuel fuel, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        fuel.setCard(cardId);
        fuelService.saveRefueling(fuel);
        counterService.toggleToFalse(cardId.getId());
        return "redirect:/home/card/" + cardId.getId();
    }

    @GetMapping("/fuel/edit/{id}")
    public String showEditFuelForm(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        if(cardService.checkIfCardIsDone(userDetails.getUserId())) {
            return "redirect:/home/card/" + cardId.getId() + "/?false";
        } else {
            Fuel fuel = fuelService.findFuelById(id);
            model.addAttribute("fuel", fuel);
            return "fuel_form";
        }
    }

    @GetMapping("/fuel/delete/{id}")
    public String deleteFuel(@PathVariable("id") Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Card cardId = cardService.findByUserId(userDetails.getUserId());
        if(cardService.checkIfCardIsDone(userDetails.getUserId())) {
            return "redirect:/home/card/" + cardId.getId() + "/?false";
        } else {
            fuelService.deleteFuel(id);
            counterService.toggleToFalse(cardId.getId());
            return "redirect:/home/card/" + cardId.getId();
        }
    }
}
