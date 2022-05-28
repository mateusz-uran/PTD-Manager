package io.github.mateuszuran.PTD.Manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/home")
    public String showHomePage() {
        return "index";
    }
}
