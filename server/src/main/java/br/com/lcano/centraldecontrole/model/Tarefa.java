package br.com.lcano.centraldecontrole.model;

import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "tarefa")
@Entity(name = "tarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcategoria", nullable = false)
    private CategoriaTarefa categoria;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "datainclusao", nullable = false)
    private Date dataInclusao;

    @Column(name = "dataprazo", nullable = false)
    private Date dataPrazo;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean finalizado;

    public Tarefa(Usuario usuario, CategoriaTarefa categoria, String titulo, String descricao, Date dataPrazo, Boolean finalizado) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInclusao = DateUtil.getDataAtual();
        this.dataPrazo = dataPrazo;
        this.finalizado = finalizado;
    }
}
