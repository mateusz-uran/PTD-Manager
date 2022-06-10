package io.github.mateuszuran.PTD.Manager.Vehicle;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import io.github.mateuszuran.PTD.Manager.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String truckMainModel;
    private String truckLicensePlate;
    private String truckType;
    private String trailerLicensePlate;
    private String trailerType;
    private Integer leftTankFuelCapacity;
    private Integer rightTankFuelCapacity;
    private Integer adBlueCapacity;
    private Integer trailerCapacity;
    private String vehicleImageName;
    private String vehicleImageDescription;
    private String vehicleImagePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Transient
    public int getVehicleFuelCapacity() {
        return leftTankFuelCapacity + rightTankFuelCapacity;
    }
}
