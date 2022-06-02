package io.github.mateuszuran.PTD.Manager.Fuel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    List<Fuel> findAllByCardId(Integer id);
}
