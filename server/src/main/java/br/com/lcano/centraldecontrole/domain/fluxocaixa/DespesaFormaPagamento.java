package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "despesaformapagamento")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class DespesaFormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public DespesaFormaPagamento(String descricao) {
        this.descricao = descricao;
    }
}
