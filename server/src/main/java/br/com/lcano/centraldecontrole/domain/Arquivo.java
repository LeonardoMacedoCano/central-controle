package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Table(name = "arquivo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Arquivo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String extensao;

    @Column(nullable = false)
    private byte[] conteudo;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(name = "dataimportacao", nullable = false)
    private Date dataImportacao;

}
