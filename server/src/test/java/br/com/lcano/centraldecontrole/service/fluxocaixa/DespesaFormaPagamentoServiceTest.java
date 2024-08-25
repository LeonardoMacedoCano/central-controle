package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaFormaPagamentoDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaFormaPagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DespesaFormaPagamentoServiceTest {
    @Mock
    private DespesaFormaPagamentoRepository despesaFormaPagamentoRepository;
    @InjectMocks
    private DespesaFormaPagamentoService despesaFormaPagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaFormaPagamentoService = new DespesaFormaPagamentoService(despesaFormaPagamentoRepository);
    }

    @Test
    void testGetTodasFormaPagamento() {
        List<DespesaFormaPagamento> formaPagamentos = Arrays.asList(
                new DespesaFormaPagamento(1L, "Cartão de Crédito"),
                new DespesaFormaPagamento(2L, "Pix")
        );
        when(despesaFormaPagamentoRepository.findAll()).thenReturn(formaPagamentos);

        List<DespesaFormaPagamentoDTO> formaPagamentoDTOs = despesaFormaPagamentoService.getTodasFormaPagamento();

        assertEquals(2, formaPagamentoDTOs.size());
        assertEquals("Cartão de Crédito", formaPagamentoDTOs.get(0).getDescricao());
        assertEquals("Pix", formaPagamentoDTOs.get(1).getDescricao());
    }

    @Test
    void testGetFormaPagamentoById() {
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento(1L, "Cartão de Crédito");
        when(despesaFormaPagamentoRepository.findById(1L)).thenReturn(Optional.of(formaPagamento));

        DespesaFormaPagamento result = despesaFormaPagamentoService.getFormaPagamentoById(1L);

        assertNotNull(result);
        assertEquals(formaPagamento.getId(), result.getId());
        assertEquals(formaPagamento.getDescricao(), result.getDescricao());
    }

    @Test
    void testGetFormaPagamentoById_NotFound() {
        when(despesaFormaPagamentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DespesaException.FormaPagamentoNaoEncontradaById.class, () -> despesaFormaPagamentoService.getFormaPagamentoById(1L));
    }
}
