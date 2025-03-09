package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "notificacao")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Notificacao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "datahora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private String link;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean visto;
}
