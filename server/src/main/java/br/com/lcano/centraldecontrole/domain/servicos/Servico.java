package br.com.lcano.centraldecontrole.domain.servicos;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "servico")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column
    private Integer porta;

    @ManyToOne
    @JoinColumn(name = "idarquivo")
    private Arquivo arquivo;

    @OneToMany(mappedBy = "servico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ServicoCategoriaRel> servicoCategoriaRel;
}
