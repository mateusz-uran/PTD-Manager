package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User();
        user.setEmail("admin@o2.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("admin"));
        user.setFirstName("Mateusz");
        user.setLastName("Uranowski");
        User savedUser = userRepository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findUserByEmail() {
        String email = "admin@o2.pl";
        User userEmail = userRepository.findByEmail(email);
        assertThat(userEmail).isNotNull();
    }
}
