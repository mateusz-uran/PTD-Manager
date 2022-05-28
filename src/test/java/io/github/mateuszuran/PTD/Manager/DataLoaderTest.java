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
    void shouldReturnDefaultNumberOfUsers() {
        List<User> listUsers = userRepository.findAll();
        assertEquals(1, listUsers.size());
    }

    @Test
    void shouldReturnNumberOfRoles() {
        List<Role> listRoles = roleRepository.findAll();
        assertEquals(3, listRoles.size());
    }
}