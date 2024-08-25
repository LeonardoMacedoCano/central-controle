package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LancamentoTest {

    @Test
    void testConstrutor() {
        Date dataLancamento = new Date();
        Usuario usuario = new Usuario();

        Lancamento lancamento = new Lancamento(1L, usuario, dataLancamento, "teste", TipoLancamentoEnum.DESPESA);
        assertEquals(1L, lancamento.getId());
        assertEquals(usuario, lancamento.getUsuario());
        assertEquals(dataLancamento, lancamento.getDataLancamento());
        assertEquals("teste", lancamento.getDescricao());
        assertEquals(TipoLancamentoEnum.DESPESA, lancamento.getTipo());
    }

}