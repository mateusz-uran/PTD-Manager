package io.github.mateuszuran.PTD.Manager.Fuel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    List<Fuel> findAllByCardId(Integer id);

    Optional<List<Fuel>> findAllByCardIdOrderByVehicleOdometer(Integer id);
}
