package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByNumber(String number);

    List<Card> findByUserId(Integer id);
}
