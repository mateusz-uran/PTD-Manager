package io.github.mateuszuran.PTD.Manager.Card;

import io.github.mateuszuran.PTD.Manager.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Card(final Integer id, final String number, final String authorFullName) {
        this.id = id;
        this.number = number;
        this.authorFullName = authorFullName;
    }
}
