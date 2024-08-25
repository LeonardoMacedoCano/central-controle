package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.*;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaParcelaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaFormaPagamentoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaParcelaRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DespesaParcelaServiceTest {
    @Mock
    private DespesaParcelaRepository despesaParcelaRepository;
    @Mock
    private DespesaFormaPagamentoService despesaFormaPagamentoService;
    @Mock
    private DateUtil dateUtil;
    @InjectMocks
    private DespesaParcelaService despesaParcelaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaParcelaService = new DespesaParcelaService(despesaParcelaRepository, despesaFormaPagamentoService, dateUtil);
    }

    @Test
    void testSalvarParcelas() {
        List<DespesaParcela> parcelas = Arrays.asList(new DespesaParcela(), new DespesaParcela(), new DespesaParcela());
        despesaParcelaService.salvarParcelas(parcelas);
        verify(despesaParcelaRepository, times(1)).saveAll(any());
    }

    @Test
    public void testCriarParcela() {
        DespesaParcelaDTO parcelaDTO = new DespesaParcelaDTO();
        parcelaDTO.setNumero(1);
        parcelaDTO.setDataVencimento(new Date());
        parcelaDTO.setValor(100.0);
        parcelaDTO.setPago(false);

        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento(1L, "Pix");

        parcelaDTO.setFormaPagamento(DespesaFormaPagamentoDTO.converterParaDTO(formaPagamento));

        Despesa despesa = new Despesa();

        when(despesaFormaPagamentoService.getFormaPagamentoById(formaPagamento.getId())).thenReturn(formaPagamento);

        DespesaParcela novaParcela = despesaParcelaService.createParcela(parcelaDTO, despesa);

        assertEquals(parcelaDTO.getNumero(), novaParcela.getNumero());
        assertEquals(parcelaDTO.getDataVencimento(), novaParcela.getDataVencimento());
        assertEquals(parcelaDTO.getValor(), novaParcela.getValor());
        assertEquals(parcelaDTO.getPago(), novaParcela.isPago());
        assertEquals(parcelaDTO.getFormaPagamento().getId(), novaParcela.getFormaPagamento().getId());
        assertEquals(parcelaDTO.getFormaPagamento().getDescricao(), novaParcela.getFormaPagamento().getDescricao());
    }

    @Test
    public void testCriarParcelas() {
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento(1L, "Pix");
        Despesa despesa = new Despesa();

        List<DespesaParcelaDTO> parcelasDTO = new ArrayList<>();
        DespesaParcelaDTO parcelaDTO1 = new DespesaParcelaDTO();
        parcelaDTO1.setNumero(1);
        parcelaDTO1.setDataVencimento(new Date());
        parcelaDTO1.setValor(100.0);
        parcelaDTO1.setPago(false);
        parcelaDTO1.setFormaPagamento(DespesaFormaPagamentoDTO.converterParaDTO(formaPagamento));

        parcelasDTO.add(parcelaDTO1);

        DespesaParcelaDTO parcelaDTO2 = new DespesaParcelaDTO();
        parcelaDTO2.setNumero(2);
        parcelaDTO2.setDataVencimento(new Date());
        parcelaDTO2.setValor(150.0);
        parcelaDTO2.setPago(false);
        parcelasDTO.add(parcelaDTO2);

        when(despesaFormaPagamentoService.getFormaPagamentoById(formaPagamento.getId())).thenReturn(formaPagamento);

        List<DespesaParcela> parcelas = despesaParcelaService.criarParcelas(despesa, parcelasDTO);

        assertEquals(parcelasDTO.size(), parcelas.size());
        assertEquals(parcelaDTO1.getNumero(), parcelas.get(0).getNumero());
        assertEquals(parcelaDTO1.getDataVencimento(), parcelas.get(0).getDataVencimento());
        assertEquals(parcelaDTO1.getValor(), parcelas.get(0).getValor());
        assertEquals(parcelaDTO1.getPago(), parcelas.get(0).isPago());
        assertEquals(parcelaDTO1.getFormaPagamento().getId(), parcelas.get(0).getFormaPagamento().getId());
        assertEquals(parcelaDTO1.getFormaPagamento().getDescricao(), parcelas.get(0).getFormaPagamento().getDescricao());

        assertEquals(parcelaDTO2.getNumero(), parcelas.get(1).getNumero());
        assertEquals(parcelaDTO2.getDataVencimento(), parcelas.get(1).getDataVencimento());
        assertEquals(parcelaDTO2.getValor(), parcelas.get(1).getValor());
        assertEquals(parcelaDTO2.getPago(), parcelas.get(1).isPago());
        assertNull(parcelas.get(1).getFormaPagamento());
    }

    @Test
    void testEditarParcela() {
        DespesaFormaPagamento formaPagamentoCartao = new DespesaFormaPagamento(1L, "Cartão de Crédito");
        DespesaFormaPagamento formaPagamentoPix = new DespesaFormaPagamento(2L, "Pix");

        DespesaParcela parcelaExistente = new DespesaParcela();
        parcelaExistente.setId(1L);
        parcelaExistente.setNumero(1);
        parcelaExistente.setDataVencimento(new Date());
        parcelaExistente.setValor(100.0);
        parcelaExistente.setPago(false);
        parcelaExistente.setFormaPagamento(formaPagamentoCartao);

        DespesaParcelaDTO parcelaDTO = new DespesaParcelaDTO();
        parcelaDTO.setId(1L);
        parcelaDTO.setNumero(1);
        parcelaDTO.setDataVencimento(new Date());
        parcelaDTO.setValor(200.0);
        parcelaDTO.setPago(true);
        parcelaDTO.setFormaPagamento(DespesaFormaPagamentoDTO.converterParaDTO(formaPagamentoPix));

        when(despesaFormaPagamentoService.getFormaPagamentoById(formaPagamentoPix.getId())).thenReturn(formaPagamentoPix);

        despesaParcelaService.updateParcela(parcelaExistente, parcelaDTO);

        assertEquals(parcelaDTO.getDataVencimento(), parcelaExistente.getDataVencimento());
        assertEquals(parcelaDTO.getValor(), parcelaExistente.getValor());
        assertEquals(parcelaDTO.getPago(), parcelaExistente.isPago());
        assertEquals(parcelaDTO.getFormaPagamento().getId(), parcelaExistente.getFormaPagamento().getId());
        assertEquals(parcelaDTO.getFormaPagamento().getDescricao(), parcelaExistente.getFormaPagamento().getDescricao());
    }

    @Test
    public void testAtualizarParcelas() {
        Despesa despesaExistente = new Despesa();
        despesaExistente.setId(1L);
        despesaExistente.setParcelas(new ArrayList<>());

        DespesaParcela parcelaExistente = new DespesaParcela();
        parcelaExistente.setId(1L);
        parcelaExistente.setNumero(1);
        parcelaExistente.setDataVencimento(new Date());
        parcelaExistente.setValor(100.0);
        parcelaExistente.setPago(false);
        despesaExistente.getParcelas().add(parcelaExistente);

        DespesaParcelaDTO parcelaDTO1 = new DespesaParcelaDTO();
        parcelaDTO1.setId(1L);
        parcelaDTO1.setNumero(1);
        parcelaDTO1.setDataVencimento(new Date());
        parcelaDTO1.setValor(200.0);
        parcelaDTO1.setPago(true);

        DespesaParcelaDTO parcelaDTO2 = new DespesaParcelaDTO();
        parcelaDTO2.setNumero(2);
        parcelaDTO2.setDataVencimento(new Date());
        parcelaDTO2.setValor(300.0);
        parcelaDTO2.setPago(false);

        List<DespesaParcelaDTO> parcelasDTO = List.of(parcelaDTO1, parcelaDTO2);

        List<DespesaParcela> parcelasAtualizadas = despesaParcelaService.updateParcelas(despesaExistente, parcelasDTO);

        assertEquals(2, parcelasAtualizadas.size());

        DespesaParcela parcelaAtualizada1 = parcelasAtualizadas.get(0);
        assertNotNull(parcelaAtualizada1.getId());
        assertEquals(parcelaDTO1.getDataVencimento(), parcelaAtualizada1.getDataVencimento());
        assertEquals(parcelaDTO1.getValor(), parcelaAtualizada1.getValor());
        assertEquals(parcelaDTO1.getPago(), parcelaAtualizada1.isPago());

        DespesaParcela parcelaAtualizada2 = parcelasAtualizadas.get(1);
        assertNull(parcelaAtualizada2.getId());
        assertEquals(parcelaDTO2.getNumero(), parcelaAtualizada2.getNumero());
        assertEquals(parcelaDTO2.getDataVencimento(), parcelaAtualizada2.getDataVencimento());
        assertEquals(parcelaDTO2.getValor(), parcelaAtualizada2.getValor());
        assertEquals(parcelaDTO2.getPago(), parcelaAtualizada2.isPago());
    }

    @Test
    void testListarParcelasPorVencimento() {
        int ano = 2024;
        int mes = 3;

        Date dataInicio = dateUtil.toDate(dateUtil.getPrimeiroDiaDoMes(ano, mes));
        Date dataFim = dateUtil.toDate(dateUtil.getUltimoDiaDoMes(ano, mes));

        Usuario usuario = new Usuario("teste", "123", new Date());
        DespesaCategoria despesaCategoria = new DespesaCategoria("teste");
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento("Cartao");
        Lancamento lancamento = new Lancamento(1L, usuario, new Date(), "teste1", TipoLancamentoEnum.DESPESA);
        Despesa despesa = new Despesa(1L, lancamento, despesaCategoria, new ArrayList<>());

        List<DespesaParcela> parcelasMock = Arrays.asList(
                new DespesaParcela(1L, 1, dataInicio, 10.00, false, despesa, formaPagamento),
                new DespesaParcela(2L, 2, dataInicio, 10.00, false, despesa, formaPagamento),
                new DespesaParcela(3L, 3, dataFim, 10.00, false, despesa, formaPagamento));

        despesa.setParcelas(parcelasMock);

        when(despesaParcelaRepository.findByDespesaAndDataVencimentoBetween(despesa, dataInicio, dataFim))
                .thenReturn(parcelasMock);

        List<DespesaParcela> parcelas = despesaParcelaService.getParcelasPorVencimento(despesa, ano, mes);

        assertEquals(parcelasMock.size(), parcelas.size());
        assertEquals(parcelasMock, parcelas);
    }

    @Test
    public void testCalcularValorTotalParcelasMensal() {
        Usuario usuario = new Usuario("teste", "123", new Date());
        DespesaCategoria despesaCategoria = new DespesaCategoria("teste");
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento("Cartao");
        Lancamento lancamento = new Lancamento(1L, usuario, new Date(), "teste1", TipoLancamentoEnum.DESPESA);
        Despesa despesa = new Despesa(1L, lancamento, despesaCategoria, new ArrayList<>());

        Date dataInicioMes = dateUtil.toDate(dateUtil.getPrimeiroDiaDoMes(2024, Calendar.JUNE));
        Date dataFimMes = dateUtil.toDate(dateUtil.getUltimoDiaDoMes(2024, Calendar.JUNE));

        List<DespesaParcela> parcelasMock = Arrays.asList(
                new DespesaParcela(1L, 1, dataInicioMes, 7.00, false, despesa, formaPagamento),
                new DespesaParcela(3L, 3, dataFimMes, 10.00, false, despesa, formaPagamento));

        when(despesaParcelaRepository.findByDataVencimentoBetween(any(), any())).thenReturn(parcelasMock);

        double total = despesaParcelaService.calcularValorTotalParcelasMensal(2024, Calendar.JUNE);

        assertEquals(17.0, total);
    }
}
