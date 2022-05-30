package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/home")
@Controller
public class VehicleController {
    private final UserRepository userRepository;

    public VehicleController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/vehicle")
    public String showVehicleForm(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle_form";
    }
}
