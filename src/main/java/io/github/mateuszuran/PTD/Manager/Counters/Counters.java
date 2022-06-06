package io.github.mateuszuran.PTD.Manager.Counters;

import io.github.mateuszuran.PTD.Manager.Card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
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
    private boolean upToDate = true;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Counters(final Integer counterTripEnd, final Integer counterTripStart, final Integer sumCounterMileage, final Integer sumCounterRefueling, final Card card) {
        this.counterTripEnd = counterTripEnd;
        this.counterTripStart = counterTripStart;
        this.sumCounterMileage = sumCounterMileage;
        this.sumCounterRefueling = sumCounterRefueling;
        this.card = card;
    }
}
