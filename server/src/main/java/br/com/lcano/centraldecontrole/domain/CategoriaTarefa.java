package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriatarefa")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
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
