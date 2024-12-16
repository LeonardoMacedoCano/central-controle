package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.Ativo;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Receita;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Table(name = "lancamento")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Lancamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "datalancamento", nullable = false)
    private Date dataLancamento;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLancamentoEnum tipo;

    @OneToOne(mappedBy = "lancamento", cascade = CascadeType.ALL)
    private Despesa despesa;

    @OneToOne(mappedBy = "lancamento", cascade = CascadeType.ALL)
    private Receita receita;

    @OneToOne(mappedBy = "lancamento", cascade = CascadeType.ALL)
    private Ativo ativo;
}
