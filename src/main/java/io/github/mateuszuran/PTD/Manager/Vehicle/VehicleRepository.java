package io.github.mateuszuran.PTD.Manager.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByUserId(Integer id);

    Optional<Vehicle> findById(Integer id);
}
