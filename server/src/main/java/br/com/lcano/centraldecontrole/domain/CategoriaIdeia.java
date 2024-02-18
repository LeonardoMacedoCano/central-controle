package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriaideia")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
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
