package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.Despesa;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaCategoriaRepository;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class DespesaServiceTest {
    @Mock
    private DespesaRepository despesaRepository;
    @Mock
    private DespesaCategoriaRepository despesaCategoriaRepository;
    @Mock
    private DespesaCategoriaService despesaCategoriaService;
    @Mock
    private DespesaFormaPagamentoService despesaFormaPagamentoService;
    @InjectMocks
    private DespesaService despesaService;

    private DespesaDTO despesaDTO;
    private Lancamento lancamento;
    private DespesaCategoria categoriaAlimentacao;
    private Despesa despesaExistente;
    private DespesaParcela parcela;
    private final Long lancamentoId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaService = new DespesaService(despesaRepository, despesaCategoriaRepository, despesaCategoriaService, despesaFormaPagamentoService);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(1L);
        categoriaDTO.setDescricao("Alimentação");

        despesaDTO = new DespesaDTO();
        despesaDTO.setCategoria(categoriaDTO);

        DespesaParcelaDTO parcelaDTO = new DespesaParcelaDTO();
        parcelaDTO.setNumero(1);
        parcelaDTO.setValor(100.0);

        despesaDTO.setParcelasDTO(List.of(parcelaDTO));

        lancamento = new Lancamento();
        lancamento.setId(1L);

        categoriaAlimentacao = new DespesaCategoria("Alimentação");
        categoriaAlimentacao.setId(1L);

        despesaExistente = new Despesa();
        despesaExistente.setId(1L);
        despesaExistente.setCategoria(categoriaAlimentacao);

        parcela = new DespesaParcela();
        parcela.setNumero(1);
        parcela.setValor(100.0);
    }

    @Test
    void testCreate() {
        when(despesaCategoriaService.getCategoriaDespesaById(1L)).thenReturn(categoriaAlimentacao);
        when(despesaParcelaService.criarParcelas(any(Despesa.class), anyList())).thenReturn(List.of(parcela));

        despesaService.create(despesaDTO, lancamento);

        verify(despesaCategoriaService, times(1)).getCategoriaDespesaById(1L);
        verify(despesaParcelaService, times(1)).criarParcelas(any(Despesa.class), anyList());
        verify(despesaRepository, times(1)).save(any(Despesa.class));
        verify(despesaParcelaService, times(1)).salvarParcelas(anyList());
    }

    @Test
    void testUpdate() {
        when(despesaRepository.findByLancamentoIdWithParcelas(lancamentoId)).thenReturn(Optional.of(despesaExistente));
        when(despesaCategoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaAlimentacao));
        when(despesaParcelaService.updateParcelas(despesaExistente, despesaDTO.getParcelasDTO())).thenReturn(List.of(parcela));

        despesaService.update(lancamentoId, despesaDTO);

        verify(despesaRepository, times(1)).findByLancamentoIdWithParcelas(lancamentoId);
        verify(despesaCategoriaRepository, times(1)).findById(1L);
        verify(despesaParcelaService, times(1)).updateParcelas(despesaExistente, despesaDTO.getParcelasDTO());
        verify(despesaRepository, times(1)).save(despesaExistente);
        verify(despesaParcelaService, times(1)).salvarParcelas(despesaExistente.getParcelas());

        assertEquals(categoriaAlimentacao, despesaExistente.getCategoria());
        assertEquals(1, despesaExistente.getParcelas().size());
        assertEquals(parcela, despesaExistente.getParcelas().get(0));
    }

    @Test
    void testDelete() {
        when(despesaRepository.findByLancamentoId(lancamentoId)).thenReturn(Optional.of(despesaExistente));

        despesaService.delete(lancamentoId);

        verify(despesaRepository, times(1)).findByLancamentoId(lancamentoId);
        verify(despesaRepository, times(1)).delete(despesaExistente);
    }

    @Test
    void testGet() {
        when(despesaRepository.findByLancamentoIdWithParcelas(lancamentoId)).thenReturn(Optional.of(despesaExistente));

        DespesaDTO resultado = despesaService.get(lancamentoId);

        verify(despesaRepository, times(1)).findByLancamentoIdWithParcelas(lancamentoId);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Alimentação", resultado.getCategoria().getDescricao());
    }

    @Test
    void testGetTipoLancamento() {
        TipoLancamentoEnum tipoLancamento = despesaService.getTipoLancamento();

        assertEquals(TipoLancamentoEnum.DESPESA, tipoLancamento);
    }

    @Test
    void testGetDespesaById_Success() {
        Long despesaId = 1L;
        when(despesaRepository.findById(despesaId)).thenReturn(Optional.of(despesaExistente));

        Despesa resultado = despesaService.getDespesaById(despesaId);

        verify(despesaRepository, times(1)).findById(despesaId);
        assertEquals(despesaExistente, resultado);
    }

    @Test
    void testGetDespesaById_NotFound() {
        Long despesaId = 1L;
        when(despesaRepository.findById(despesaId)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaById.class, () -> despesaService.getDespesaById(despesaId));
        verify(despesaRepository, times(1)).findById(despesaId);
    }

    @Test
    void testGetCategoriaById_Success() {
        Long categoriaId = 1L;
        when(despesaCategoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaAlimentacao));

        DespesaCategoria resultado = despesaService.getCategoriaById(categoriaId);

        verify(despesaCategoriaRepository, times(1)).findById(categoriaId);
        assertEquals(categoriaAlimentacao, resultado);
    }

    @Test
    void testGetCategoriaById_NotFound() {
        Long categoriaId = 1L;
        when(despesaCategoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        assertThrows(DespesaException.CategoriaNaoEncontradaById.class, () -> despesaService.getCategoriaById(categoriaId));
        verify(despesaCategoriaRepository, times(1)).findById(categoriaId);
    }

    @Test
    void testGetDespesaByLancamentoIdWithParcelas_Success() {
        when(despesaRepository.findByLancamentoIdWithParcelas(lancamentoId)).thenReturn(Optional.of(despesaExistente));

        Despesa resultado = despesaService.getDespesaByLancamentoIdWithParcelas(lancamentoId);

        verify(despesaRepository, times(1)).findByLancamentoIdWithParcelas(lancamentoId);
        assertEquals(despesaExistente, resultado);
    }

    @Test
    void testGetDespesaByLancamentoIdWithParcelas_NotFound() {
        when(despesaRepository.findByLancamentoIdWithParcelas(lancamentoId)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaByLancamentoId.class, () -> despesaService.getDespesaByLancamentoIdWithParcelas(lancamentoId));
        verify(despesaRepository, times(1)).findByLancamentoIdWithParcelas(lancamentoId);
    }

    @Test
    void testGetDespesaByLancamentoId_Success() {
        when(despesaRepository.findByLancamentoId(lancamentoId)).thenReturn(Optional.of(despesaExistente));

        Despesa resultado = despesaService.getDespesaByLancamentoId(lancamentoId);

        verify(despesaRepository, times(1)).findByLancamentoId(lancamentoId);
        assertEquals(despesaExistente, resultado);
    }

    @Test
    void testGetDespesaByLancamentoId_NotFound() {
        when(despesaRepository.findByLancamentoId(lancamentoId)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaByLancamentoId.class, () -> despesaService.getDespesaByLancamentoId(lancamentoId));
        verify(despesaRepository, times(1)).findByLancamentoId(lancamentoId);
    }

    @Test
    void testGetDespesaByIdWithParcelas_Success() {
        Long despesaId = 1L;
        when(despesaRepository.findByIdWithParcelas(despesaId)).thenReturn(Optional.of(despesaExistente));

        Despesa resultado = despesaService.getDespesaByIdWithParcelas(despesaId);

        verify(despesaRepository, times(1)).findByIdWithParcelas(despesaId);
        assertEquals(despesaExistente, resultado);
    }

    @Test
    void testGetDespesaByIdWithParcelas_NotFound() {
        Long despesaId = 1L;
        when(despesaRepository.findByIdWithParcelas(despesaId)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaById.class, () -> despesaService.getDespesaByIdWithParcelas(despesaId));
        verify(despesaRepository, times(1)).findByIdWithParcelas(despesaId);
    }

    /*
    @Test
    public void testListarDespesaResumoMensalDTO() {
        Long idUsuario = 1L;
        Integer ano = 2024;
        Integer mes = 2;

        Usuario usuario = new Usuario("teste", "123", new Date());

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("teste");

        Despesa despesa1 = new Despesa(1L, usuario, categoriaDespesa, "teste1", new Date(), new ArrayList<>());
        Despesa despesa2 = new Despesa(2L, usuario, categoriaDespesa, "teste2", new Date(), new ArrayList<>());

        DespesaParcela despesaParcela1 = new DespesaParcela();
        DespesaParcela despesaParcela2 = new DespesaParcela();
        despesa1.setParcelas(Arrays.asList(despesaParcela1, despesaParcela2));

        List<Despesa> despesas = Arrays.asList(despesa1, despesa2);
        when(despesaRepository.findByUsuarioId(idUsuario)).thenReturn(despesas);
        when(despesaParcelaService.listarParcelasPorVencimento(despesa1, ano, mes)).thenReturn(Arrays.asList(despesaParcela1, despesaParcela2));
        when(despesaParcelaService.listarParcelasPorVencimento(despesa2, ano, mes)).thenReturn(new ArrayList<>());

        Pageable pageable = PageRequest.of(0, 10);
        Page<DespesaResumoMensalDTO> despesasDTOPage = despesaService.listarDespesaResumoMensalDTO(idUsuario, ano, mes, pageable);

        assertEquals(1, despesasDTOPage.getContent().size());
        assertEquals(1, despesasDTOPage.getTotalPages());
        assertEquals(1, despesasDTOPage.getTotalElements());

        verify(despesaParcelaService, times(1)).listarParcelasPorVencimento(despesa1, ano, mes);
        verify(despesaParcelaService, times(1)).listarParcelasPorVencimento(despesa2, ano, mes);
    }
     */
}
