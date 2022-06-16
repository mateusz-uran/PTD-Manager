package io.github.mateuszuran.PTD.Manager.Fuel;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelService {
    private final FuelRepository fuelRepository;

    public FuelService(final FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public void saveRefueling(Fuel fuel) {
        fuelRepository.save(fuel);
    }

    public Fuel findFuelById(Integer id) {
        return fuelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Refueling with given id not found"));
    }

    public List<Fuel> findAllFuelsFromCard(Integer id) {
        return fuelRepository.findAllByCardId(id);
    }

    public List<Fuel> findAllAndSort(Integer id) {
        return fuelRepository.findAllByCardIdOrderByVehicleOdometer(id).orElseThrow(() -> new IllegalArgumentException("Refueling with given id not found"));
    }

    public  void deleteFuel(Integer id) {
        fuelRepository.deleteById(id);
    }
}
