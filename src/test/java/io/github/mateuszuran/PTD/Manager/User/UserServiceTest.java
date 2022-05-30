package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    void shouldReturnWithDefaultRole() {
        List<User> listUsers = userRepository.findAll();
        var result = listUsers.get(listUsers.size()-1);
        assertThat(result.getRoles().contains("User"));
    }

    @Test
    void checkIfCodeWasGenerated() {
        var result = userService.generateRegistrationCode();
        assertThat(result, notNullValue());
    }
}