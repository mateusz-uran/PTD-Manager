package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    private final UserService userService;

    public AppController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHomePage() {
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
        return "register";
    }

    @PostMapping("/process_register")
    public String registerUser(User user) {
        userService.setUserWithDefaultRole(user);
        return "redirect:/register?success";
    }
}
