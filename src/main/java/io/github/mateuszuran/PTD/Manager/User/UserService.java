package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Role.Role;
import io.github.mateuszuran.PTD.Manager.Role.RoleRepository;
import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public UserService(final RoleRepository roleRepository, final UserRepository userRepository, final CodeRepository codeRepository, final JavaMailSender javaMailSender, final PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.mailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
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

    public void saveGeneratedCode() {
        Code code = new Code();
        code.setNumber(generateRegistrationCode());
        code.setActive(true);
        code.setUsedBy("Kod nie używany");

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
        return codeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Code with given id not found"));
    }

    public void getFullNameUserFromCode(Integer id, String number) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        Code getUsedCode = codeRepository.findByNumber(number);
        var fullName = user.getFirstName() + " " + user.getLastName();
        getUsedCode.setUsedBy(fullName);
        codeRepository.save(getUsedCode);
    }

    public void deleteCode(Integer id) {
        codeRepository.deleteById(id);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void save(User user) {
        String encodedPassword = user.getPassword();
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean checkIfUserExists(User user) {
        return !emailExists(user.getEmail());
    }

    public boolean checkIfEditedUserEmailIsUnique(String email, Integer id) {
        var result = userRepository.findByEmail(email);
        return !Objects.equals(result.getId(), id);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "ptd-manager@onet.pl";
        String senderName = "PTD-Manager";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "PTD-Manager";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.fullName());
        String verifyURL = siteURL + "/verify?verCode=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public void register(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        user.addRole(roleRepository.findByName("User"));
        userRepository.save(user);

        sendVerificationEmail(user, siteURL);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }

    }
}
