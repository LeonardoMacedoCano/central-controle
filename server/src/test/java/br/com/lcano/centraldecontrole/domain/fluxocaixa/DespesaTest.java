package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
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
        CategoriaDespesa categoria = new CategoriaDespesa();
        String descricao = "Teste";
        Date dataLancamento = new Date();
        List<DespesaParcela> parcelas = new ArrayList<>();

        Despesa despesa = new Despesa(id, usuario, categoria, descricao, dataLancamento, parcelas);

        assertEquals(id, despesa.getId());
        assertEquals(usuario, despesa.getUsuario());
        assertEquals(categoria, despesa.getCategoria());
        assertEquals(descricao, despesa.getDescricao());
        assertEquals(dataLancamento, despesa.getDataLancamento());
        assertEquals(parcelas, despesa.getParcelas());
    }

    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        Despesa despesa1 = new Despesa(id, new Usuario(), new CategoriaDespesa(), "Teste", new Date(), new ArrayList<>());
        Despesa despesa2 = new Despesa(id, new Usuario(), new CategoriaDespesa(), "Teste", new Date(), new ArrayList<>());

        assertTrue(despesa1.equals(despesa2) && despesa2.equals(despesa1));
        assertEquals(despesa1.hashCode(), despesa2.hashCode());
    }

    @Test
    void testToString() {
        Long id = 1L;
        Despesa despesa = new Despesa(id, new Usuario(), new CategoriaDespesa(), "Teste", new Date(), new ArrayList<>());
        assertEquals("Despesa(id=1)", despesa.toString());
    }
}
