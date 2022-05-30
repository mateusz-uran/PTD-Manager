package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final UserService userService;
    private final CodeRepository codeRepository;

    public AppController(final UserService userService, final CodeRepository codeRepository) {
        this.userService = userService;
        this.codeRepository = codeRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<User> listUsers = userService.findAllUsers();
        model.addAttribute("listUsers", listUsers);
        List<Code> listCodes = codeRepository.findAll();
        model.addAttribute("listCodes", listCodes);
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
        if(userService.checkIfCodeExists(code)) {
            userService.setUserWithDefaultRole(user);
            userService.toggleCodeWhenUsed(code);
            userService.getFullNameUserFromCode(user.getId(), code.getNumber());
            return "redirect:/register?success";
        } return "redirect:/register?false";
    }
}
