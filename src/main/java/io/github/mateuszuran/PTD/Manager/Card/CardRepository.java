package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByNumber(String number);

    List<Card> findAllCardsByUserId(Integer id);

    Card findByUserId(Integer id);

    Card findByNumber(String number);

    Card findByUserEmail(String number);

    Card findCardIdByNumber(String number);
}
