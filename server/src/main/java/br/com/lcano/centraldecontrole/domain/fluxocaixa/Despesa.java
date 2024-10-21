package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.DespesaFormaPagamentoEnum;
import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Table(name = "despesa")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Despesa implements Serializable {
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
    private DespesaCategoria categoria;

    @Column(name = "datavencimento", nullable = false)
    private Date dataVencimento;

    @Column(nullable = false)
    private double valor;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean pago;

    @Enumerated(EnumType.STRING)
    @Column(name = "formapagamento", nullable = false)
    private DespesaFormaPagamentoEnum formaPagamento;
}
