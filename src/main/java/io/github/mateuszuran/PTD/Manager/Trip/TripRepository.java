package io.github.mateuszuran.PTD.Manager.Trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findAllByCardId(Integer id);
}
