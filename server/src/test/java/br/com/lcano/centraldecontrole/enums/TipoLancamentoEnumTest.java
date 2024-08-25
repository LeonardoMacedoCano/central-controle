package br.com.lcano.centraldecontrole.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoLancamentoEnumTest {

    @Test
    public void testDescricao() {
        assertEquals("DESPESA", TipoLancamentoEnum.DESPESA.getDescricao());
        assertEquals("RECEITA", TipoLancamentoEnum.RECEITA.getDescricao());
        assertEquals("PASSIVO", TipoLancamentoEnum.PASSIVO.getDescricao());
        assertEquals("ATIVO", TipoLancamentoEnum.ATIVO.getDescricao());
    }

    @Test
    public void testEnumValues() {
        TipoLancamentoEnum[] values = TipoLancamentoEnum.values();
        assertEquals(4, values.length);

        assertTrue(contains(values, TipoLancamentoEnum.DESPESA));
        assertTrue(contains(values, TipoLancamentoEnum.RECEITA));
        assertTrue(contains(values, TipoLancamentoEnum.PASSIVO));
        assertTrue(contains(values, TipoLancamentoEnum.ATIVO));
    }

    private boolean contains(TipoLancamentoEnum[] array, TipoLancamentoEnum value) {
        for (TipoLancamentoEnum enumValue : array) {
            if (enumValue == value) {
                return true;
            }
        }
        return false;
    }
}