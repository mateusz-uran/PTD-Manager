package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Role.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserService(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void setUserWithDefaultRole(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleRepository.findByName("User"));
        userRepository.save(user);
    }
}
