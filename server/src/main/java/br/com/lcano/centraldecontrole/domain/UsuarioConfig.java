package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuarioconfig")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class UsuarioConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Column(name = "despesanumeroitempagina", columnDefinition = "INT DEFAULT 10")
    private int despesaNumeroItemPagina;

    @Column(name = "despesavalormetamensal")
    private double despesaValorMetaMensal;

    @Column(name = "despesadiapadraovencimento", columnDefinition = "INT DEFAULT 10")
    private int despesaDiaPadraoVencimento;

    @ManyToOne
    @JoinColumn(name = "despesaidformapagamentopadrao")
    private FormaPagamento despesaFormaPagamentoPadrao;

    public UsuarioConfig(Usuario usuario) {
        this.usuario = usuario;
        this.despesaNumeroItemPagina = 10;
        this.despesaValorMetaMensal = 0.0;
        this.despesaDiaPadraoVencimento = 10;
    }
}
