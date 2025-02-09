package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "fluxocaixaparametro", uniqueConstraints = {
        @UniqueConstraint(columnNames = "idusuario", name = "ukfluxocaixaparametrousuario")
})
public class FluxoCaixaParametro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcategoriadespesapadrao")
    private DespesaCategoria despesaCategoriaPadrao;

    @Column(name = "metalimitedespesamensal")
    private BigDecimal metaLimiteDespesaMensal;

    @ManyToOne
    @JoinColumn(name = "idcategoriarendapadrao")
    private RendaCategoria rendaCategoriaPadrao;

    @ManyToOne
    @JoinColumn(name = "idcategoriarendapassiva")
    private RendaCategoria rendaPassivaCategoria;

    @Column(name = "metaaportemensal")
    private BigDecimal metaAporteMensal;
    
    @Column(name = "metaaportetotal", precision = 15, scale = 2)
    private BigDecimal metaAporteTotal;

    @Column(name = "diapadraovencimentocartao", nullable = false)
    private Long diaPadraoVencimentoCartao;
}
