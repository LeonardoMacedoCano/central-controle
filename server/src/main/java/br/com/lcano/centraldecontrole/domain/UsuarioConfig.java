package br.com.lcano.centraldecontrole.domain;

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

    @Column(name = "despesavalormetamensal")
    private double despesaValorMetaMensal;

    public UsuarioConfig(Usuario usuario) {
        this.usuario = usuario;
        this.despesaValorMetaMensal = 0.0;
    }
}
