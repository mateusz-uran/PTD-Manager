package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class VehicleController {
    private final VehicleService vehicleService;
    private final UserService userService;

    public VehicleController(final VehicleService service, final UserService userService) {
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
        return "redirect:/";
    }

    @GetMapping("/vehicle/edit/{id}")
    public String editVehicle(@PathVariable("id") Integer id, Model model) {
        List<User> listUsers = userService.findAllUsers();
        Vehicle vehicle = vehicleService.findVehicleById(id);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("vehicle", vehicle);
        return "vehicle_form";
    }

    @GetMapping("/vehicle/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Integer id) {
        vehicleService.deleteVehicleById(id);
        return "redirect:/";
    }
}
