package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardService;
import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import io.github.mateuszuran.PTD.Manager.Security.CustomUserDetails;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import io.github.mateuszuran.PTD.Manager.Vehicle.Vehicle;
import io.github.mateuszuran.PTD.Manager.Vehicle.VehicleRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {
    private final UserService userService;
    private final CodeRepository codeRepository;
    private final VehicleRepository vehicleRepository;
    private final CardService cardService;

    public AppController(final UserService userService, final CodeRepository codeRepository, final VehicleRepository vehicleRepository,  final CardService cardService) {
        this.userService = userService;
        this.codeRepository = codeRepository;
        this.vehicleRepository = vehicleRepository;
        this.cardService = cardService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if(cardService.hasRole()) {
            List<Card> listCards = cardService.findAllCards();
            model.addAttribute("listCards", listCards);
        } else {
            List<Card> listCards = cardService.findAllByUserId(userDetails.getUserId());
            model.addAttribute("listCards", listCards);
        }
        List<User> listUsers = userService.findAllUsers();
        model.addAttribute("listUsers", listUsers);
        List<Code> listCodes = codeRepository.findAll();
        model.addAttribute("listCodes", listCodes);
        List<Vehicle> listVehicles = vehicleRepository.findAll();
        model.addAttribute("listVehicles", listVehicles);

        model.addAttribute("card", new Card());
        model.addAttribute("vehicle", new Vehicle());

        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("code", new Code());
        return "register";
    }

    @PostMapping("/process_register")
    public String registerUser(User user, Code code) {
        if(userService.checkIfCodeExists(code) && userService.checkIfUserExists(user)) {
            userService.setUserWithDefaultRole(user);
            userService.toggleCodeWhenUsed(code);
            userService.getFullNameUserFromCode(user.getId(), code.getNumber());
            return "redirect:/register?success";
        } return "redirect:/register?false";
    }
}
