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
        userRepository.save(new User(2, "foo@o2.pl", "foobar", "John", "Smith"));
        User user = userRepository.findById(2).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(1, "toggleCard", "foobar", "4.01.1997", true, user));
        cardService.toggleCard(2);
        var result = cardRepository.findByNumber("toggleCard");
        assertFalse(result.isDone());
//        assertFalse(cardService.checkIfCardIsDone(1));
    }
}