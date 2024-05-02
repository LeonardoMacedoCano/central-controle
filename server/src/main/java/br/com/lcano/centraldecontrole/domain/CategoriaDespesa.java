package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "categoriadespesa")
@Entity
public class CategoriaDespesa extends Categoria {
    public CategoriaDespesa() {
        super();
    }

    public CategoriaDespesa(String descricao) {
        super(descricao);
    }
}
