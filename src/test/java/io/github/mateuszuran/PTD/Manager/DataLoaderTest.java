package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.Role.Role;
import io.github.mateuszuran.PTD.Manager.Role.RoleRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DataLoaderTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void shouldReturnAdminAsDefaultUser() {
        List<User> listUsers = userRepository.findAll();
        User user = userRepository.findByEmail("admin@o2.pl");
        assertEquals(user.getEmail(), "admin@o2.pl");
    }

    @Test
    void shouldReturnNumberOfRoles() {
        List<Role> listRoles = roleRepository.findAll();
        assertEquals(3, listRoles.size());
    }
}