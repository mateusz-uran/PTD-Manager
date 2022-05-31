package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/home")
@Controller
public class VehicleController {
    private final UserRepository userRepository;
    private final AmazonClient amazonClient;
    private final VehicleRepository vehicleRepository;

    public VehicleController(final UserRepository userRepository, final AmazonClient amazonClient, final VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.amazonClient = amazonClient;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/vehicle")
    public String showVehicleForm(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle_form";
    }

    @PostMapping("/add-vehicle")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, Vehicle vehicle) {
        var result = amazonClient.uploadFile(file, vehicle);
        vehicle.setVehicleImagePath(result);
        vehicleRepository.save(vehicle);
        return "redirect:/home";
    }
}
