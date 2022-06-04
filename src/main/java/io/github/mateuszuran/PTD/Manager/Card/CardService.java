package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void saveCard(Card card) {
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

    public void deleteCard(Integer id) {
        cardRepository.deleteById(id);
    }

    public boolean hasRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("Admin"));
    }

    public String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void toggleCard(Integer id) {
        Card card = cardRepository.findByUserId(id);
        card.setDone(!card.isDone());
        cardRepository.save(card);
    }

    public Card findCard(Integer id) {
        return cardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Card of given id not exists"));
    }

    public boolean checkIfCardIsDone(Integer id) {
        Card card = cardRepository.findByUserId(id);
        return card.isDone();
    }
}
