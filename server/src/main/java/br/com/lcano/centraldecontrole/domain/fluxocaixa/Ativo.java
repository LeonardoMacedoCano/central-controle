package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoCategoria;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoOperacaoExtratoMovimentacaoB3;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ativo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Ativo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idlancamento", nullable = false)
    private Lancamento lancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private AtivoCategoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "operacao", nullable = false)
    private TipoOperacaoExtratoMovimentacaoB3 operacao;

    @Column(name = "datamovimento", nullable = false)
    private Date dataMovimento;

    @Column(nullable = false)
    private BigDecimal valor;
}
