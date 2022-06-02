package io.github.mateuszuran.PTD.Manager.Fuel;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fuel")
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 10)
    private String refuelingDate;
    @Column(length = 20)
    private String refuelingLocation;
    @Column(length = 15)
    private Integer vehicleOdometer;
    @Column(length = 15, nullable = false)
    private Integer fuelAmount;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
