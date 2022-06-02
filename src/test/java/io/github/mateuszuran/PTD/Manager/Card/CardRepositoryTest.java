package io.github.mateuszuran.PTD.Manager.Card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    void shouldReturnTrueIfCardExists() {
        Card card = new Card(anyInt(), "number", "author");
        cardRepository.save(card);
        assertTrue(cardRepository.existsByNumber(card.getNumber()));
    }

}