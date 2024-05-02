package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "formapagamento")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public FormaPagamento(String descricao) {
        this.descricao = descricao;
    }
}
