package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoConta;
import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "extratocontaregra")
@Entity
@Data
public class ExtratoContaRegra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tiporegra", nullable = false)
    private TipoRegraExtratoConta tipoRegra;

    @Column(name = "descricaomatch", nullable = false)
    private String descricaoMatch;

    @Column(name = "descricaodestino")
    private String descricaoDestino;

    @Column(name = "idcategoriadestino")
    private Long idCategoria;

    @Column(nullable = false)
    private Long prioridade;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean ativo;
}
