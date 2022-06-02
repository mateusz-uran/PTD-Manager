package io.github.mateuszuran.PTD.Manager.Card;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByNumber(String number);
}
