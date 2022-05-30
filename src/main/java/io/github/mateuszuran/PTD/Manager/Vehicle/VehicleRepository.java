package io.github.mateuszuran.PTD.Manager.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByUserId(Integer id);
}
