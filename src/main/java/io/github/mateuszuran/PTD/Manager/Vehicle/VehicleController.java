package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/home")
@Controller
public class VehicleController {
    private final UserRepository userRepository;
    private final AmazonClient amazonClient;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final UserService userService;

    public VehicleController(final UserRepository userRepository, final AmazonClient amazonClient, final VehicleRepository vehicleRepository, final VehicleService service, final UserService userService) {
        this.userRepository = userRepository;
        this.amazonClient = amazonClient;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = service;
        this.userService = userService;
    }

    @GetMapping("/vehicle")
    public String showVehicleForm(Model model) {
        List<User> listUsers = userService.findAllUsers();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle_form";
    }

    @PostMapping("/add-vehicle")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, Vehicle vehicle) {
        vehicleService.saveVehicle(vehicle, file);
        return "redirect:/home";
    }

    @GetMapping("/vehicle/edit/{id}")
    public String editVehicle(@PathVariable("id") Integer id, Model model) {
        List<User> listUsers = userService.findAllUsers();
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("vehicle", vehicle);
        return "vehicle_form";
    }

    @GetMapping("/vehicle/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Integer id) {
        vehicleService.deleteVehicleById(id);
        return "redirect:/home";
    }
}
