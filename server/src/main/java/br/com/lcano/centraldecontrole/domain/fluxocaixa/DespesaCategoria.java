package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "despesacategoria")
@Entity
public class DespesaCategoria extends Categoria {
    public DespesaCategoria() {
        super();
    }

    public DespesaCategoria(String descricao) {
        super(descricao);
    }
}
