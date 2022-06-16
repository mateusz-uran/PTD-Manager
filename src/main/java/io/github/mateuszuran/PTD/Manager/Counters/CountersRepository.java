package io.github.mateuszuran.PTD.Manager.Counters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountersRepository extends JpaRepository<Counters, Integer> {
    Optional<Counters> findByCardId(Integer id);
}
