package io.github.mateuszuran.PTD.Manager.Counters;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "counters")
public class Counters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 15, nullable = false)
    private Integer counterTripEnd;
    @Column(length = 15, nullable = false)
    private Integer counterTripStart;
    @Column(length = 15, nullable = false)
    private Integer sumCounterMileage;
    @Column(length = 10, nullable = false)
    private Integer sumCounterRefueling;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
