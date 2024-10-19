package br.com.lcano.centraldecontrole.domain.servicos;

import br.com.lcano.centraldecontrole.domain.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicocategoria")
public class ServicoCategoria extends Categoria {

    @Column(nullable = false)
    private String icone;

    public ServicoCategoria() {
        super();
    }

    public ServicoCategoria(String descricao, String icone) {
        super(descricao);
        this.icone = icone;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
