package br.com.lcano.centraldecontrole.domain.servicos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicocategoriarel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class ServicoCategoriaRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idservico", nullable = false)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "idservicocategoria", nullable = false)
    private ServicoCategoria servicoCategoria;
}
