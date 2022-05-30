package io.github.mateuszuran.PTD.Manager.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void shouldReturnWithDefaultRole() {
        List<User> listUsers = userRepository.findAll();
        var result = listUsers.get(listUsers.size()-1);
        assertThat(result.getRoles().contains("User"));
    }
}