package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaDespesaTest {

    @Test
    void testConstrutorVazio() {
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        assertNotNull(categoriaDespesa);
    }

    @Test
    void testConstrutorComDescricao() {
        String descricao = "Teste";
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa(descricao);
        assertEquals(descricao, categoriaDespesa.getDescricao());
    }

    @Test
    void testHeranca() {
        String descricao = "Teste";
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa(descricao);
        assertEquals(descricao, categoriaDespesa.getDescricao());

        categoriaDespesa.setDescricao("Nova descrição");
        assertEquals("Nova descrição", categoriaDespesa.getDescricao());
    }

    @Test
    void testAnnotationTable() {
        Table tableAnnotation = CategoriaDespesa.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("categoriadespesa", tableAnnotation.name());
    }
}
