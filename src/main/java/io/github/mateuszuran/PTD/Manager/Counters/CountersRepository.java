package io.github.mateuszuran.PTD.Manager.Counters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountersRepository extends JpaRepository<Counters, Integer> {
    List<Counters> findAllByCardId(Integer id);

    Optional<Counters> findByCardId(Integer id);
}
