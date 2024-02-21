package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "categoriaideia")
@Entity
public class CategoriaIdeia extends Categoria {
    public CategoriaIdeia() {
        super();
    }

    public CategoriaIdeia(String descricao) {
        super(descricao);
    }
}
