package io.github.mateuszuran.PTD.Manager.User;

import io.github.mateuszuran.PTD.Manager.Security.Code;
import io.github.mateuszuran.PTD.Manager.Security.CodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CodeRepository codeRepository;

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

    @Test
    void checkIfCodeWasToggled() {
        var test = new Code(1, "code", true, "");
        codeRepository.save(test);
        userService.toggleCodeWhenUsed(test);
        var result = codeRepository.findByNumber(test.getNumber());
        assertThat(result);
    }

    @Test
    void toggleCode() {
        Code testCode = codeRepository.save(new Code(1, "code", false, ""));
        userService.toggle(testCode);
        codeRepository.save(testCode);
        assertTrue("Code toggled to true", testCode.isActive());
    }

    @Test
    void setFullNameUsedByUser() {
        Code code = new Code(1, "number", true, "");
        codeRepository.save(code);
        userService.getFullNameUserFromCode(1, "number");
        var usedCode = codeRepository.findByNumber("number");
        assertEquals(usedCode.getUsedBy(), "Mateusz Uranowski");
    }

    @Test
    void getUserById() {
        var result = userService.get(1);
        assertThat(result, notNullValue());
    }

    @Test
    void shouldGetRoles() {
        var result = userService.getRoles();
        assertThat(result, notNullValue());
    }

    @Test
    void shouldDeleteCodeById() {
        Code newCode = new Code(1, "number", true, "anonymo");
        codeRepository.save(newCode);
        userService.deleteCode(1);
        assertThat(codeRepository.findById(1).isEmpty());
    }

    @Test
    void shouldReturnFalseIfUserExists() {
        User user = new User(2, "test@o2.pl",  "password","Foo", "Bar");
        userRepository.save(user);
        assertFalse("User exists", userService.checkIfUserExists(user));
    }
}