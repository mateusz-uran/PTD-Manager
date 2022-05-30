package io.github.mateuszuran.PTD.Manager.Security;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CodeRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(CodeRepositoryTest.class);

    @Autowired
    CodeRepository codeRepository;

    @Test
    void checkIfCodeExists() {
        Code code = new Code();
        code.setNumber("number");
        codeRepository.save(code);
        assertTrue(codeRepository.existsByNumber(code.getNumber()));
    }

    @Test
    void findCodeByNumberTest() {
        Code code = new Code();
        code.setNumber("number");
        codeRepository.save(code);
        assertEquals(codeRepository.findByNumber(code.getNumber()), code);
    }
}