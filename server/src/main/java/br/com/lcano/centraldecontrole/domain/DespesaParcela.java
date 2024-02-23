package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Table(name = "parcela")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class DespesaParcela implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numero;

    @Column(name = "datavencimento", nullable = false)
    private Date dataVencimento;

    @Column(nullable = false)
    private double valor;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean pago;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddespesa")
    private Despesa despesa;
}
