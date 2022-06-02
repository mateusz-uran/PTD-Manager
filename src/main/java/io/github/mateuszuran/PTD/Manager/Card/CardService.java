package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> findAllByUserId(Integer id) {
        return cardRepository.findAllCardsByUserId(id);
    }

    public Card findByUserId(Integer id) {
        return cardRepository.findByUserId(id);
    }

    public boolean hasRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("Admin"));
    }
}
