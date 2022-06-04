package io.github.mateuszuran.PTD.Manager.Trip;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(final TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAllTripsFromCard(Integer id) {
        return tripRepository.findAllByCardId(id);
    }

    public List<Trip> findAllAndSort(Integer id) {
        return tripRepository.findAllByCardIdOrderByTripStartVehicleCounter(id);
    }

    public Trip findTripById(Integer id) {
        return tripRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trip with given id not found"));
    }

    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public  void deleteTrip(Integer id) {
        tripRepository.deleteById(id);
    }
}
