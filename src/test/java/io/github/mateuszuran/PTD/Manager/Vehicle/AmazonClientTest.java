package io.github.mateuszuran.PTD.Manager.Vehicle;

import com.amazonaws.services.s3.AmazonS3;
import io.github.mateuszuran.PTD.Manager.AppController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AmazonClientTest {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private AmazonClient client;

    @Test
    void shouldConvertMultipartFileToFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello World".getBytes());
        var result = client.convertMultiPartToFile(multipartFile);
        assertTrue(result.isFile());
    }

    @Test
    void shouldGetLocalTime() {
        MockMultipartFile multipartFile = new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello World".getBytes());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        var result = client.generateFileName(multipartFile);
        assertTrue(result.contains(dtf.format(now)));
    }

    @Test
    public void shouldInitializeS3Bucket() {
        assertThat(new AmazonClient()).isNotNull();
    }

    @Test
    void shouldCheckIfS3BucketExists() {
        client.isBucketExists();
        assertTrue(client.isBucketExists());
    }
}