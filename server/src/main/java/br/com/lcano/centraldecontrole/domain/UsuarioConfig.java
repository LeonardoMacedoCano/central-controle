package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FormaPagamento;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Table(name = "usuarioconfig")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class UsuarioConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "despesanumeromaxitempagina", columnDefinition = "INT DEFAULT 10")
    private int despesaNumeroMaxItemPagina;

    @Column(name = "despesavalormetamensal")
    private double despesaValorMetaMensal;

    @Column(name = "despesadiapadraovencimento", columnDefinition = "INT DEFAULT 10")
    private int despesaDiaPadraoVencimento;

    @ManyToOne
    @JoinColumn(name = "despesaidformapagamentopadrao")
    private FormaPagamento despesaFormaPagamentoPadrao;

    public UsuarioConfig(Usuario usuario) {
        this.usuario = usuario;
        this.despesaNumeroMaxItemPagina = 10;
        this.despesaValorMetaMensal = 0.0;
        this.despesaDiaPadraoVencimento = 10;
    }
}
