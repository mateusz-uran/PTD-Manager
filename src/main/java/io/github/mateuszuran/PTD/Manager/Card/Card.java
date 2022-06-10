package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.Counters.Counters;
import io.github.mateuszuran.PTD.Manager.Fuel.Fuel;
import io.github.mateuszuran.PTD.Manager.Trip.Trip;
import io.github.mateuszuran.PTD.Manager.User.User;
import io.github.mateuszuran.PTD.Manager.Vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20, nullable = false, unique = true)
    private String number;
    @Column(length = 45, nullable = false)
    private String authorFullName;
    @Column(length = 20, nullable = false)
    private String createDate;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<Trip> trip = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "card")
    private List<Fuel> fuel = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "card")
    private Counters counters;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "card")
    private Vehicle vehicle;

    public Card(final Integer id, final String number, final String authorFullName, final String createDate) {
        this.id = id;
        this.number = number;
        this.authorFullName = authorFullName;
        this.createDate = createDate;
    }

    public Card(final Integer id, final String number, final String authorFullName, String createDate, final User user) {
        this.id = id;
        this.number = number;
        this.authorFullName = authorFullName;
        this.createDate = createDate;
        this.user = user;
    }

    public Card(final Integer id, final String number, final String authorFullName, final String createDate, boolean done, final User user) {
        this.id = id;
        this.number = number;
        this.authorFullName = authorFullName;
        this.createDate = createDate;
        this.done = done;
        this.user = user;
    }
}
