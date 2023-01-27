package io.github.mateuszuran.PTD.Manager.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    Optional<User> findById(Integer id);

    User findByVerificationCode(String code);

    boolean existsByEmail(String email);
}
