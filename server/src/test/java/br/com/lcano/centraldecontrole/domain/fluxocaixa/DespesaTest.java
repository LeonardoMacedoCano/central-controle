package br.com.lcano.centraldecontrole.domain.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DespesaTest {

    @Test
    void testConstrutorVazio() {
        Despesa despesa = new Despesa();
        assertNotNull(despesa);
    }

    @Test
    void testConstrutorComParametros() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        DespesaCategoria categoria = new DespesaCategoria();
        String descricao = "Teste";
        Date dataLancamento = new Date();
        List<DespesaParcela> parcelas = new ArrayList<>();

        Lancamento lancamento = new Lancamento(1L, usuario, dataLancamento, descricao, TipoLancamentoEnum.DESPESA);
        Despesa despesa = new Despesa(id, lancamento, categoria, parcelas);

        assertEquals(id, despesa.getId());
        assertEquals(usuario, lancamento.getUsuario());
        assertEquals(categoria, despesa.getCategoria());
        assertEquals(descricao, lancamento.getDescricao());
        assertEquals(dataLancamento, lancamento.getDataLancamento());
        assertEquals(parcelas, despesa.getParcelas());
    }

    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        Lancamento lancamento1 = new Lancamento(1L, new Usuario(), new Date(), "Teste", TipoLancamentoEnum.DESPESA);
        Lancamento lancamento2 = new Lancamento(1L, new Usuario(), new Date(), "Teste", TipoLancamentoEnum.DESPESA);

        Despesa despesa1 = new Despesa(id, lancamento1, new DespesaCategoria(), new ArrayList<>());
        Despesa despesa2 = new Despesa(id, lancamento2, new DespesaCategoria(), new ArrayList<>());

        assertTrue(despesa1.equals(despesa2) && despesa2.equals(despesa1));
        assertEquals(despesa1.hashCode(), despesa2.hashCode());
    }

    @Test
    void testToString() {
        Long id = 1L;
        Lancamento lancamento = new Lancamento(1L, new Usuario(), new Date(), "Teste", TipoLancamentoEnum.DESPESA);
        Despesa despesa = new Despesa(id, lancamento, new DespesaCategoria(), new ArrayList<>());
        assertEquals("Despesa(id=1)", despesa.toString());
    }
}
