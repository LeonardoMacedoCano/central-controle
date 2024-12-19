package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "fluxocaixaconfig", uniqueConstraints = {
        @UniqueConstraint(columnNames = "idusuario", name = "ukfluxocaixaconfigusuario")
})
public class FluxoCaixaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "valormetasaldomensal")
    private BigDecimal valorMetaSaldoMensal;

    @ManyToOne
    @JoinColumn(name = "idcategoriadespesapadrao")
    private DespesaCategoria despesaCategoriaPadrao;

    @ManyToOne
    @JoinColumn(name = "idcategoriareceitapadrao")
    private ReceitaCategoria receitaCategoriaPadrao;

    @Column(name = "diapadraovencimentofatura", nullable = false)
    private Long diaPadraoVencimentoFatura;
}
