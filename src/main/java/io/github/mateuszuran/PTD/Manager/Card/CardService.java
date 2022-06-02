package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void saveCarD(Card card) {
        cardRepository.save(card);
    }

    public boolean checkIfCardExists(Card card) {
        return cardRepository.existsByNumber(card.getNumber());
    }
}
