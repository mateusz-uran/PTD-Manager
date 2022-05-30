package io.github.mateuszuran.PTD.Manager.Role;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    public Role() {
    }

    public Role(final String name) {
        this.name = name;
    }

    public Role(final Integer id) {
        this.id = id;
    }

    public Role(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }
}
