package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DespesaFormaPagamentoTest {
    @Test
    void testConstrutorVazio() {
        DespesaFormaPagamento despesaFormaPagamento = new DespesaFormaPagamento();
        assertNotNull(despesaFormaPagamento);
    }

    @Test
    void testConstrutorComDescricao() {
        String descricao = "Teste";
        DespesaFormaPagamento despesaFormaPagamento = new DespesaFormaPagamento(descricao);
        assertEquals(descricao, despesaFormaPagamento.getDescricao());
    }

    @Test
    void testAnnotationTable() {
        Table tableAnnotation = DespesaFormaPagamento.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("despesaformapagamento", tableAnnotation.name());
    }
}
