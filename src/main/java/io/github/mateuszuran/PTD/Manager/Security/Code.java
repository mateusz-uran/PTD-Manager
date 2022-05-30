package io.github.mateuszuran.PTD.Manager.Security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 10)
    private String number;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false, length = 20)
    private String usedBy;

    public Code(final Integer id, final String number, final boolean active) {
        this.id = id;
        this.number = number;
        this.active = active;
    }
}
