package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "receitacategoria")
@Entity
public class ReceitaCategoria extends Categoria {
    public ReceitaCategoria() {
        super();
    }

    public ReceitaCategoria(String descricao) {
        super(descricao);
    }
}
