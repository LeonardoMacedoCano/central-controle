package br.com.lcano.centraldecontrole.domain.servicos;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuarioservico")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class UsuarioServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idservico", nullable = false)
    private Servico servico;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean permissao;
}