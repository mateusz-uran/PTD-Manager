package io.github.mateuszuran.PTD.Manager.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/generate")
    public String generateCodeForRegistration() {
        userService.saveGeneratedCode();
        return "redirect:/home";
    }
}
