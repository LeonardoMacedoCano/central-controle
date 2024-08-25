package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaCategoriaTest {

    @Test
    void testConstrutorVazio() {
        DespesaCategoria despesaCategoria = new DespesaCategoria();
        assertNotNull(despesaCategoria);
    }

    @Test
    void testConstrutorComDescricao() {
        String descricao = "Teste";
        DespesaCategoria despesaCategoria = new DespesaCategoria(descricao);
        assertEquals(descricao, despesaCategoria.getDescricao());
    }

    @Test
    void testHeranca() {
        String descricao = "Teste";
        DespesaCategoria despesaCategoria = new DespesaCategoria(descricao);
        assertEquals(descricao, despesaCategoria.getDescricao());

        despesaCategoria.setDescricao("Nova descrição");
        assertEquals("Nova descrição", despesaCategoria.getDescricao());
    }

    @Test
    void testAnnotationTable() {
        Table tableAnnotation = DespesaCategoria.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("despesacategoria", tableAnnotation.name());
    }
}
