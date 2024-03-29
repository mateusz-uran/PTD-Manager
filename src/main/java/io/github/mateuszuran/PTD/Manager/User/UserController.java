package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Role.Role;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final CodeRepository codeRepository;

    public UserController(final UserService userService, final CodeRepository codeRepository) {
        this.userService = userService;
        this.codeRepository = codeRepository;
    }

    @GetMapping("/generate")
    public String generateCodeForRegistration() {
        userService.saveGeneratedCode();
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggleCode(@PathVariable("id") Integer id) {
        var checkbox = userService.getCode(id);
        userService.toggle(checkbox);
        codeRepository.save(checkbox);
        return "redirect:/";
    }

    @GetMapping("/code/delete/{id}")
    public String deleteCode(@PathVariable("id") Integer id) {
        userService.deleteCode(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        List<Role> listRoles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "edit_user";
    }

    @PostMapping("/edit/save")
    public String saveEditedUser(User user) {
        Integer id = user.getId();
        if(userService.checkIfEditedUserEmailIsUnique(user.getEmail(), id)) {
            return "redirect:/edit/" + id + "?false";
        } else {
            userService.save(user);
            return "redirect:/edit/" + id + "?success";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }
}
