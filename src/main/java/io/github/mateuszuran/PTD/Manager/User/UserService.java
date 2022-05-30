package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Role.RoleRepository;
import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    public UserService(final RoleRepository roleRepository, final UserRepository userRepository, final CodeRepository codeRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void setUserWithDefaultRole(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleRepository.findByName("User"));
        userRepository.save(user);
    }

    public List<Code> listCodes() {
        return codeRepository.findAll();
    }

    public void saveGeneratedCode() {
        Code code = new Code();
        code.setNumber(generateRegistrationCode());
        code.setActive(true);

        codeRepository.save(code);
    }

    public String generateRegistrationCode() {
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public boolean checkIfCodeExists(Code code) {
        Code result = codeRepository.findByNumber(code.getNumber());
        return codeRepository.existsByNumber(code.getNumber()) && result.isActive();
    }

    public void toggleCodeWhenUsed(Code code) {
        Code result = codeRepository.findByNumber(code.getNumber());
        result.setActive(false);
        codeRepository.save(result);
    }

    public void toggle(Code code) {
        var checkbox = code.isActive();
        code.setActive(!checkbox);
    }

    public Code getCode(Integer id) {
        return codeRepository.findById(id).orElse(null);
    }
}
