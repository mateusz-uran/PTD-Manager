package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.Card.CardRepository;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VehicleRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(VehicleRepositoryTest.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CardRepository cardRepository;

    @Test
    @Transactional
    void getVehicleByUserId() {
        User user = userRepository.findById(1).orElse(null);
        Card card = cardRepository.save(new Card(1, "test", "foo", "bar"));
        Vehicle vehicle = new Vehicle(
                1, "truckModel", "truckPlate",
                "truckType", "trailerLicensePlate",
                "trailerType", 5, 5,
                5, 100, "vehicleImageName",
                "vehicleImageDescription", "vehicleImagePath", user, card);
        vehicleRepository.save(vehicle);
        var getVehicle = vehicleRepository.findByUserId(1);
        assertEquals(vehicle, getVehicle);
    }
}