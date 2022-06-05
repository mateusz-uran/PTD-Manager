package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class CardServiceTest {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardService cardService;


    @Test
    void shouldToggleCardAndReturnFalseIfDoneIsFalse() {
        User user = userRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(anyInt(), "toggle", "author", "1.01.1997", true, user));
        cardService.toggleCard(1);
        var result = cardRepository.findByUserId(1);
        assertFalse(result.isDone());
        assertFalse(cardService.checkIfCardIsDone(1));
    }
}