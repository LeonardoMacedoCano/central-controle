package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "regraextratocontacorrente")
@Entity
@Data
public class RegraExtratoContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tiporegra", nullable = false)
    private TipoRegraExtratoContaCorrente tipoRegra;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "descricaomatch", nullable = false)
    private String descricaoMatch;

    @Column(name = "descricaodestino")
    private String descricaoDestino;

    @ManyToOne
    @JoinColumn(name = "idcategoriadespesadestino")
    private DespesaCategoria despesaCategoriaDestino;

    @ManyToOne
    @JoinColumn(name = "idcategoriarendadestino")
    private RendaCategoria rendaCategoriaDestino;

    @Column(nullable = false)
    private Long prioridade;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean ativo;
}
