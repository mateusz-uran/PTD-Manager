package io.github.mateuszuran.PTD.Manager.Counters;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Fuel.Fuel;
import io.github.mateuszuran.PTD.Manager.Fuel.FuelService;
import io.github.mateuszuran.PTD.Manager.Trip.Trip;
import io.github.mateuszuran.PTD.Manager.Trip.TripService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalInt;

@Service
public class CounterService {
    private final CountersRepository countersRepository;
    private final TripService tripService;
    private final FuelService fuelService;

    public CounterService(final CountersRepository countersRepository, final TripService tripService, final FuelService fuelService) {
        this.countersRepository = countersRepository;
        this.tripService = tripService;
        this.fuelService = fuelService;
    }

    public List<Counters> findAllCountersById(Integer id) {
        return countersRepository.findAllByCardId(id);
    }

    public Counters findByCardId(Integer id) {
        return countersRepository.findByCardId(id).orElseThrow(() -> new IllegalArgumentException("Counters of given ID not found"));
    }

    public Counters getCountersInfo(Integer id) {
        List<Trip> listTrips = tripService.findAllTripsFromCard(id);
        OptionalInt firstCounter = listTrips.stream()
                .mapToInt(Trip::getTripStartVehicleCounter).min();
        OptionalInt lastCounter = listTrips.stream()
                .mapToInt(Trip::getTripStartVehicleCounter).max();
        int tripCourse = listTrips.stream()
                .mapToInt(Trip::getCarMileage).sum();
        List<Fuel> listRefueling = fuelService.findAllFuelsFromCard(id);
        int sumRefueling = listRefueling.stream()
                .mapToInt(Fuel::getFuelAmount).sum();
        Counters counters = countersRepository.findByCardId(id).orElseThrow(() -> new IllegalArgumentException("Counters of given ID not found"));
        counters.setCounterTripStart(firstCounter.orElse(0));
        counters.setCounterTripEnd(lastCounter.orElse(0));
        counters.setSumCounterMileage(tripCourse);
        counters.setSumCounterRefueling(sumRefueling);
        return counters;
    }

    public void saveCounters(Counters counters) {
        countersRepository.save(counters);
    }

    public void saveEmptyCounters(Card card) {
        countersRepository.save(new Counters(0, 0, 0, 0, card));
    }
}
