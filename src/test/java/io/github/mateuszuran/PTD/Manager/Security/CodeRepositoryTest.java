package io.github.mateuszuran.PTD.Manager.Security;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CodeRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(CodeRepositoryTest.class);

    @Autowired
    CodeRepository codeRepository;

    String generateCode() {
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Test
    void checkIfCodeExists() {
        Code code = new Code();
        code.setNumber(generateCode());
        code.setUsedBy("anonymo");
        codeRepository.save(code);
        assertTrue(codeRepository.existsByNumber(code.getNumber()));
    }

    @Test
    void findCodeByNumberTest() {
        Code code = new Code();
        code.setNumber(generateCode());
        code.setUsedBy("anonymo");
        codeRepository.save(code);
        assertEquals(codeRepository.findByNumber(code.getNumber()), code);
    }
}