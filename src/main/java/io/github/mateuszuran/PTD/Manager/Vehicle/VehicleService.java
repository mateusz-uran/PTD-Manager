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
        Vehicle result = vehicleRepository.findById(id).orElse(null);
        amazonClient.deleteFileFromS3Bucket(result.getVehicleImagePath());
        vehicleRepository.deleteById(id);
    }

    public Vehicle findById(Integer id) {
        return vehicleRepository.findById(id).orElse(null);
    }
}
