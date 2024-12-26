package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Table(name = "despesacategoria")
@Entity
public class DespesaCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String descricao;

    public DespesaCategoria(String descricao) {
        this.descricao = descricao;
    }
}
