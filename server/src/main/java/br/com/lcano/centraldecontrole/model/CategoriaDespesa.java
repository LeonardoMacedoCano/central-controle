package br.com.lcano.centraldecontrole.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriadespesa")
@Entity(name = "categoriadespesa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public CategoriaDespesa(String descricao) {
        this.descricao = descricao;
    }
}
