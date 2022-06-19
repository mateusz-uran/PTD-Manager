package io.github.mateuszuran.PTD.Manager.Card;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import io.github.mateuszuran.PTD.Manager.Counters.CounterService;
import io.github.mateuszuran.PTD.Manager.Counters.Counters;
import io.github.mateuszuran.PTD.Manager.Fuel.Fuel;
import io.github.mateuszuran.PTD.Manager.Fuel.FuelService;
import io.github.mateuszuran.PTD.Manager.Security.CustomUserDetails;
import io.github.mateuszuran.PTD.Manager.Trip.Trip;
import io.github.mateuszuran.PTD.Manager.Trip.TripService;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;
    private final UserService userService;
    private final TripService tripService;
    private final FuelService fuelService;
    private final CounterService counterService;
    private final ServletContext servletContext;
    private final TemplateEngine templateEngine;

    public CardController(final CardService cardService, final UserService userService,
                          final TripService tripService, final FuelService fuelService,
                          final CounterService counterService,
                          final ServletContext servletContext, final TemplateEngine templateEngine) {
        this.cardService = cardService;
        this.userService = userService;
        this.tripService = tripService;
        this.fuelService = fuelService;
        this.counterService = counterService;
        this.servletContext = servletContext;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/save-card")
    public String saveNewCard(Card card, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserById(userDetails.getUserId());
        if (cardService.checkIfCardExists(card)) {
            return "redirect:/?false";
        }
        card.setUser(user);
        card.setAuthorFullName(user.fullName());
        card.setCreateDate(cardService.currentDate());
        card.setDone(false);
        cardService.saveCard(card);
        counterService.saveEmptyCounters(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}")
    public String showCard(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Trip> listTrips = tripService.findAllAndSort(id);
        model.addAttribute("listTrips", listTrips);
        List<Fuel> listFuels = fuelService.findAllAndSort(id);
        model.addAttribute("listFuels", listFuels);
        Counters counters = counterService.findByCardId(id);
        model.addAttribute("counters", counters);
        Card card = cardService.findCard(id);
        model.addAttribute("card", card);
        if(cardService.checkCardUser(id, userDetails.getUserId())) { return "card"; }
        else { return "card_individual"; }

    }

    @GetMapping("/card/delete/{id}")
    public String deleteCard(@PathVariable("id") Integer id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(cardService.checkCardUser(id, userDetails.getUserId())) {
            cardService.deleteCard(id);
            return "redirect:/";
        } return "redirect:/?forbidden";
    }

    @GetMapping("/card/toggle/{id}")
    public String toggleCard(@PathVariable("id") Integer id) {
        if (!counterService.checkIfCardIsUpToDate(id)) {
            return "redirect:/card/" + id;
        } else {
            cardService.toggleCard(id);
        }
        return "redirect:/card/" + id;
    }

    @GetMapping("/card/pdf/{id}")
    public ResponseEntity<?> getPDF(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException {
        Card card;
        if (cardService.hasRole()) {
            card = cardService.getAllDataFromCard(id, cardService.findCard(id).getUser().getId());
        } else {
            card = cardService.getAllDataFromCard(id, userDetails.getUserId());
        }
        if (!card.isDone()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Karta nie jest zakończona");
        } else {
            WebContext context = new WebContext(request, response, servletContext);
            context.setVariable("card", card);
            String cardHtml = templateEngine.process("pdf_template", context);
            ByteArrayOutputStream target = new ByteArrayOutputStream();
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri("http://localhost:8080");

            HtmlConverter.convertToPdf(cardHtml, target, converterProperties);
            byte[] bytes = target.toByteArray();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        }
    }
}
