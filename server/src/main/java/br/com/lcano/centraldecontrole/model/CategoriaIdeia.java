package br.com.lcano.centraldecontrole.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriaideia")
@Entity(name = "categoriaideia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaIdeia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public CategoriaIdeia(String descricao) {
        this.descricao = descricao;
    }
}
