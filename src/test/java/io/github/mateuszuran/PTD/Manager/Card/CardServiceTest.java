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
        userRepository.save(new User(anyInt(), "foo@o2.pl", "foobar", "John", "Smith"));
        User user = userRepository.findByEmail("foo@o2.pl");
        cardRepository.save(new Card(anyInt(), "toggleCard", "foobar", "4.01.1997", true, user));
        Card card = cardRepository.findByNumber("toggleCard");
        cardService.toggleCard(card.getId());
        var result = cardRepository.findByNumber("toggleCard");
        assertFalse(result.isDone());
        assertFalse(cardService.checkIfCardIsDone(user.getId()));
    }
}