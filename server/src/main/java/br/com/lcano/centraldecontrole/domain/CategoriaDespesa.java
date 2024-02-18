package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoriadespesa")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
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
