package io.github.mateuszuran.PTD.Manager.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findUserByEmail() {
        String email = "admin@o2.pl";
        User userEmail = userRepository.findByEmail(email);
        assertThat(userEmail).isNotNull();
    }

    @Test
    void findUserById() {
        userRepository.save(new User(2, "test@o2.pl", "password", "Foo", "Bar"));
        User user = userRepository.findById(2).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        assertThat(user).isNotNull();
    }
}
