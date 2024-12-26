package br.com.lcano.centraldecontrole.dto;

public abstract class BaseDTO<E> {
    public abstract BaseDTO<E> fromEntity(E entity);
    public abstract E toEntity();
}
