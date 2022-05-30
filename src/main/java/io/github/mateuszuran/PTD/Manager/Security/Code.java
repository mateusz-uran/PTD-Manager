package io.github.mateuszuran.PTD.Manager.Security;

import lombok.Data;

import javax.persistence.*;

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
}
