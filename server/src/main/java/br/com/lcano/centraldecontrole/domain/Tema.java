package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "tema", uniqueConstraints = {@UniqueConstraint(columnNames = {"idusuario", "title"})})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Tema implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String title;

    @Column(name = "primarycolor", nullable = false, length = 7)
    private String primaryColor;

    @Column(name = "secondarycolor", nullable = false, length = 7)
    private String secondaryColor;

    @Column(name = "tertiarycolor", nullable = false, length = 7)
    private String tertiaryColor;

    @Column(name = "quaternarycolor", nullable = false, length = 7)
    private String quaternaryColor;

    @Column(name = "whitecolor", nullable = false, length = 7)
    private String whiteColor;

    @Column(name = "blackcolor", nullable = false, length = 7)
    private String blackColor;

    @Column(name = "graycolor", nullable = false, length = 7)
    private String grayColor;

    @Column(name = "successcolor", nullable = false, length = 7)
    private String successColor;

    @Column(name = "infocolor", nullable = false, length = 7)
    private String infoColor;

    @Column(name = "warningcolor", nullable = false, length = 7)
    private String warningColor;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(name = "isdefault", nullable = false)
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
}
