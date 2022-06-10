package io.github.mateuszuran.PTD.Manager.Trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findAllByCardId(Integer id);

    Optional<List<Trip>> findAllByCardIdOrderByTripStartVehicleCounter(Integer id);

    Optional<Trip> findById(Integer id);
}
