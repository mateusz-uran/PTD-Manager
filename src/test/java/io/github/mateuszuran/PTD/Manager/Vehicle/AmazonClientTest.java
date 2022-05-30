package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.AppController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AmazonClientTest {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private AmazonClient amazonClient;

    @Test
    void shouldConvertMultipartFileToFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello World".getBytes());
        var result = amazonClient.convertMultiPartToFile(multipartFile);
        assertTrue(result.isFile());
    }
}