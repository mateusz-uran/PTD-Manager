package io.github.mateuszuran.PTD.Manager.Trip;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 10)
    private String tripStartDay;
    @Column(length = 10)
    private String tripEndDay;
    @Column(length = 10)
    private String tripStartHour;
    @Column(length = 10)
    private String tripEndHour;
    @Column(length = 25)
    private String tripStartLocation;
    @Column(length = 25)
    private String tripEndLocation;
    @Column(length = 25)
    private String tripStartCountry;
    @Column(length = 25)
    private String tripEndCountry;
    @Column(length = 15, nullable = false)
    private Integer tripStartVehicleOdometer;
    @Column(length = 15, nullable = false)
    private Integer tripEndVehicleOdometer;
    @Column(length = 15)
    private Integer carMileage;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Integer subtract() {
        return tripEndVehicleOdometer - tripStartVehicleOdometer;
    }
}
