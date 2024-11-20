package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "ativocategoria")
@Entity
public class AtivoCategoria extends Categoria {
    public AtivoCategoria() {
        super();
    }

    public AtivoCategoria(String descricao) {
        super(descricao);
    }
}