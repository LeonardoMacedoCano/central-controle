package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import br.com.lcano.centraldecontrole.dto.FormaPagamentoDTO;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.FormaPagamentoRepository;
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

public class FormaPagamentoServiceTest {
    @Mock
    private FormaPagamentoRepository formaPagamentoRepository;

    @InjectMocks
    private FormaPagamentoService formaPagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTodasFormaPagamento() {
        List<FormaPagamento> formaPagamentos = Arrays.asList(
                new FormaPagamento(1L, "Cartão de Crédito"),
                new FormaPagamento(2L, "Pix")
        );
        when(formaPagamentoRepository.findAll()).thenReturn(formaPagamentos);

        List<FormaPagamentoDTO> formaPagamentoDTOs = formaPagamentoService.getTodasFormaPagamento();

        assertEquals(2, formaPagamentoDTOs.size());
        assertEquals("Cartão de Crédito", formaPagamentoDTOs.get(0).getDescricao());
        assertEquals("Pix", formaPagamentoDTOs.get(1).getDescricao());
    }

    @Test
    void testGetFormaPagamentoById() {
        FormaPagamento formaPagamento = new FormaPagamento(1L, "Cartão de Crédito");
        when(formaPagamentoRepository.findById(1L)).thenReturn(Optional.of(formaPagamento));

        FormaPagamento result = formaPagamentoService.getFormaPagamentoById(1L);

        assertNotNull(result);
        assertEquals(formaPagamento.getId(), result.getId());
        assertEquals(formaPagamento.getDescricao(), result.getDescricao());
    }

    @Test
    void testGetFormaPagamentoById_NotFound() {
        when(formaPagamentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DespesaException.FormaPagamentoNaoEncontradaById.class, () -> formaPagamentoService.getFormaPagamentoById(1L));
    }
}
