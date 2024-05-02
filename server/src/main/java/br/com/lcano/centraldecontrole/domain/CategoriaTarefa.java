package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "categoriatarefa")
@Entity
public class CategoriaTarefa extends Categoria {
    public CategoriaTarefa() {
        super();
    }

    public CategoriaTarefa(String descricao) {
        super(descricao);
    }
}
