package io.github.mateuszuran.PTD.Manager.Security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    boolean existsByNumber(String number);

    Code findByNumber(String number);
}
