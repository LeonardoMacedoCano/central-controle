package com.backend.centraldecontrole.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriatarefa")
@Entity(name = "categoriatarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaTarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public CategoriaTarefa(String descricao) {
        this.descricao = descricao;
    }
}
