package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DespesaParcela> parcelas = new ArrayList<>();
}
