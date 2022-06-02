package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void shouldReturnTrueIfCardExists() {
        Card card = new Card(anyInt(), "number", "author");
        cardRepository.save(card);
        assertTrue(cardRepository.existsByNumber(card.getNumber()));
    }

    @Test
    void shouldReturnAlLCardsAssignmentToUser() {
        User user = userRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        cardRepository.save(new Card(anyInt(), "number", "author", user));
        assertThat(cardRepository.findAllCardsByUserId(1)).isNotNull();
        assertThat(cardRepository.findByUserId(1)).isNotNull();
    }

}