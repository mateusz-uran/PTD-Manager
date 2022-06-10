package io.github.mateuszuran.PTD.Manager.Vehicle;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final AmazonClient amazonClient;

    public VehicleService(final VehicleRepository vehicleRepository, final AmazonClient amazonClient) {
        this.vehicleRepository = vehicleRepository;
        this.amazonClient = amazonClient;
    }

    public Vehicle saveVehicle(Vehicle vehicle, MultipartFile file) {
        var result = amazonClient.uploadFile(file, vehicle);
        vehicle.setVehicleImagePath(result);
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Integer id) {
        Vehicle result = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehicle with given id not found"));
        amazonClient.deleteFileFromS3Bucket(result.getVehicleImageName());
        vehicleRepository.deleteById(id);
    }

    public Vehicle findVehicleById(Integer id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehicle with given id not found"));
    }

    public Vehicle findVehicleByUserId(Integer id) {
        return vehicleRepository.findByUserId(id).orElseThrow(() -> new IllegalArgumentException("Vehicle with given id not found"));
    }
}
