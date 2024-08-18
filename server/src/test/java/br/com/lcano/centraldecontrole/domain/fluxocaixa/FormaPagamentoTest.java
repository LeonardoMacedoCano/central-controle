package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FormaPagamento;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FormaPagamentoTest {
    @Test
    void testConstrutorVazio() {
        FormaPagamento formaPagamento = new FormaPagamento();
        assertNotNull(formaPagamento);
    }

    @Test
    void testConstrutorComDescricao() {
        String descricao = "Teste";
        FormaPagamento formaPagamento = new FormaPagamento(descricao);
        assertEquals(descricao, formaPagamento.getDescricao());
    }

    @Test
    void testAnnotationTable() {
        Table tableAnnotation = FormaPagamento.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("formapagamento", tableAnnotation.name());
    }
}
