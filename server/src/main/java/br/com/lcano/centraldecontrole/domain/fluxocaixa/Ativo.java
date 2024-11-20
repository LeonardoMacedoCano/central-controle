package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.AtivoOperacaoEnum;
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

    @ManyToOne
    @JoinColumn(name = "idcategoria", nullable = false)
    private AtivoCategoria categoria;

    @Column(nullable = false)
    private String ticker;

    @Enumerated(EnumType.STRING)
    @Column(name = "operacao", nullable = false)
    private AtivoOperacaoEnum operacao;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal quantidade;

    @Column(name = "precounitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "datamovimento", nullable = false)
    private Date dataMovimento;
}
