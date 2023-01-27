package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.Role.Role;
import io.github.mateuszuran.PTD.Manager.Role.RoleRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataLoader(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        if(userRepository.existsByEmail("admin@o2.pl")) {
        }
            userRepository.save(createAdmin());
        var roles = roleRepository.findAll();
        if(roles.isEmpty()) {
        }
            roleRepository.saveAll(addRoles());
        if(userRepository.existsByEmail("admin@o2.pl") && roles.contains(roleRepository.findByName("Admin"))) {
        }
            addRoleToDefaultUser();
    }

    User createAdmin() {
        User user = new User();
        user.setEmail("admin@o2.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("admin"));
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEnabled(true);
        return user;
    }

    List<Role> addRoles() {
        Role user = new Role("User");
        Role admin = new Role("Admin");
        Role owner = new Role("Owner");
        return List.of(user, admin, owner);
    }

    void addRoleToDefaultUser() {
        User user = userRepository.findById(1).orElse(null);
        user.addRole(roleRepository.findByName("Admin"));
        userRepository.save(user);
    }
}
