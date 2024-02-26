package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.dto.DespesaParcelaDTO;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import br.com.lcano.centraldecontrole.repository.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class DespesaServiceTest {
    @Mock
    private DespesaRepository despesaRepository;
    @Mock
    private CategoriaDespesaRepository categoriaDespesaRepository;
    @Mock
    private CategoriaDespesaService categoriaDespesaService;
    @Mock
    private DespesaParcelaService despesaParcelaService;
    @InjectMocks
    private DespesaService despesaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaService = new DespesaService(despesaRepository, categoriaDespesaRepository, categoriaDespesaService, despesaParcelaService);
    }

    @Test
    public void testGetCategoriaById_CategoriaExiste() {
        Long idCategoria = 1L;
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();

        when(categoriaDespesaRepository.findById(idCategoria)).thenReturn(Optional.of(categoriaDespesa));

        CategoriaDespesa result = despesaService.getCategoriaById(idCategoria);

        assertEquals(categoriaDespesa, result);
    }

    @Test
    public void testGetCategoriaById_CategoriaNaoExiste() {
        Long idCategoria = 1L;

        when(categoriaDespesaRepository.findById(idCategoria)).thenReturn(Optional.empty());

        assertThrows(DespesaException.CategoriaDespesaNaoEncontradaById.class, () -> despesaService.getCategoriaById(idCategoria));
    }

    @Test
    public void testGetDespesaById_DespesaExiste() {
        Long idDespesa = 1L;
        Despesa despesa = new Despesa();

        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesa));

        Despesa result = despesaService.getDespesaById(idDespesa);

        assertEquals(despesa, result);
    }

    @Test
    public void testGetDespesaById_DespesaNaoExiste() {
        Long idDespesa = 1L;

        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaById.class, () -> despesaService.getDespesaById(idDespesa));
    }

    @Test
    public void testCriarDespesa() {
        Long idCategoria = 1L;
        String descricao = "Despesa de teste";
        Usuario usuario = new Usuario();
        DespesaDTO despesaDTO = new DespesaDTO();
        despesaDTO.setIdCategoria(idCategoria);
        despesaDTO.setDescricao(descricao);

        when(categoriaDespesaService.getCategoriaDespesaById(idCategoria)).thenReturn(new CategoriaDespesa());

        Despesa despesa = despesaService.criarDespesa(despesaDTO, usuario);

        assertNotNull(despesa);
        assertEquals(usuario, despesa.getUsuario());
        assertNotNull(despesa.getCategoria());
        assertEquals(descricao, despesa.getDescricao());
    }

    @Test
    public void testListarDespesasDoUsuarioPorVencimento() {
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

        List<DespesaDTO> despesasDTO = despesaService.listarDespesasDoUsuarioPorVencimento(idUsuario, ano, mes);

        assertEquals(1, despesasDTO.size());
        verify(despesaParcelaService, times(2)).listarParcelasPorVencimento(any(Despesa.class), eq(ano), eq(mes));
    }


    @Test
    public void testGerarDespesa() {
        Long idCategoria = 1L;

        List<DespesaParcelaDTO> parcelasDTO = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            DespesaParcelaDTO parcelaDTO = new DespesaParcelaDTO();
            parcelaDTO.setNumero(i);
            parcelaDTO.setDataVencimento(new Date());
            parcelaDTO.setValor(10.0);
            parcelasDTO.add(parcelaDTO);
        }

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("teste");
        usuario.setAtivo(true);

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(idCategoria);
        when(categoriaDespesaService.getCategoriaDespesaById(idCategoria)).thenReturn(categoriaDespesa);

        DespesaDTO despesaDTO = new DespesaDTO();
        despesaDTO.setIdCategoria(idCategoria);
        despesaDTO.setDescricao("teste");
        despesaDTO.setParcelasDTO(parcelasDTO);

        Despesa novaDespesa = new Despesa();
        novaDespesa.setUsuario(usuario);
        novaDespesa.setCategoria(categoriaDespesa);
        novaDespesa.setDescricao(despesaDTO.getDescricao());
        List<DespesaParcela> parcelas = despesaParcelaService.criarParcelas(novaDespesa, parcelasDTO);
        novaDespesa.getParcelas().addAll(parcelas);

        when(despesaParcelaService.criarParcelas(any(Despesa.class), anyList())).thenReturn(parcelas);

        despesaService.gerarDespesa(despesaDTO, usuario);

        verify(despesaRepository, times(1)).save(any(Despesa.class));
    }

    @Test
    public void testEditarDespesa() {
        Long idDespesa = 1L;
        Long idCategoria = 2L;
        String descricao = "Descrição da despesa";
        Usuario usuario = new Usuario();
        DespesaDTO despesaDTO = new DespesaDTO();
        despesaDTO.setIdCategoria(idCategoria);
        despesaDTO.setDescricao(descricao);

        Despesa despesaExistente = new Despesa();
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(idCategoria);
        when(categoriaDespesaRepository.findById(idCategoria)).thenReturn(Optional.of(categoriaDespesa));

        despesaService.editarDespesa(idDespesa, despesaDTO, usuario);

        verify(despesaRepository).findById(idDespesa);
        verify(despesaRepository).save(despesaExistente);

        assertEquals(categoriaDespesa, despesaExistente.getCategoria());
        assertEquals(usuario, despesaExistente.getUsuario());
        assertEquals(descricao, despesaExistente.getDescricao());
    }

    @Test
    public void testExcluirDespesa() {
        Long idDespesa = 1L;

        Despesa despesaExistente = new Despesa();
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        despesaService.excluirDespesa(idDespesa);

        verify(despesaRepository).findById(idDespesa);
        verify(despesaRepository).delete(despesaExistente);
    }
}
