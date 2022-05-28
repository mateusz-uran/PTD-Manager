package io.github.mateuszuran.PTD.Manager;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;

    public DataLoader(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        userRepository.save(createAdmin());
    }

    User createAdmin() {
        User user = new User();
        user.setEmail("admin@o2.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("admin"));
        user.setFirstName("Mateusz");
        user.setLastName("Uranowski");
        return user;
    }
}
