package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.DespesaRequestDTO;
import br.com.lcano.centraldecontrole.dto.DespesaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import br.com.lcano.centraldecontrole.repository.DespesaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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

    @InjectMocks
    private DespesaService despesaService;

    @Test
    void testGerarDespesa() {
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(1L, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        when(categoriaDespesaRepository.findById(1L)).thenReturn(Optional.of(categoriaDespesa));

        despesaService.gerarDespesa(requestDTO, usuario);

        verify(despesaRepository, times(1)).save(any());
    }

    @Test
    void testGerarDespesa_CategoriaNaoEncontrada() {
        Long idCategoriaNaoExistente = 999L;
        DespesaRequestDTO data = new DespesaRequestDTO(idCategoriaNaoExistente, "Descrição", 100.0, null);
        Usuario usuario = new Usuario();

        assertThrows(DespesaException.CategoriaDespesaNaoEncontradaById.class,() -> despesaService.gerarDespesa(data, usuario));
    }

    @Test
    public void testListarDespesasDoUsuario() {
        int anoFiltro = 2024;
        int mesFiltro = 1;
        LocalDate primeiroDiaDoMes = LocalDate.of(anoFiltro, mesFiltro, 1);
        LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
        Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Usuario usuario = new Usuario("usuario teste", "senha123", new Date());
        usuario.setId(1L);
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("Categoria teste");

        Despesa despesa1 = new Despesa(usuario, categoriaDespesa, "despesa 1", 1.00, new Date());
        Despesa despesa2 = new Despesa(usuario, categoriaDespesa, "despesa 2", 2.00, new Date());

        List<Despesa> listaDespesas = Arrays.asList(despesa1, despesa2);

        when(despesaRepository.findByUsuarioIdAndDataBetween(usuario.getId(), dataInicio, dataFim)).thenReturn(listaDespesas);

        List<DespesaResponseDTO> resultado = despesaService.listarDespesasDoUsuario(usuario.getId(), anoFiltro, mesFiltro);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        DespesaResponseDTO despesaResponseDTO1 = resultado.get(0);
        assertEquals(despesa1.getId(), despesaResponseDTO1.id());
        assertEquals(despesa1.getDescricao(), despesaResponseDTO1.descricao());

        DespesaResponseDTO despesaResponseDTO2 = resultado.get(1);
        assertEquals(despesa2.getId(), despesaResponseDTO2.id());
        assertEquals(despesa2.getDescricao(), despesaResponseDTO2.descricao());
    }

    @Test
    public void testEditarDespesa() {
        Long idDespesa = 1L;
        DespesaRequestDTO despesaRequestDTO = new DespesaRequestDTO(idDespesa, "teste", 10.00, new Date());
        Usuario usuario = new Usuario("username", "senha", new Date());

        CategoriaDespesa categoria = new CategoriaDespesa("categoria teste");
        when(categoriaDespesaRepository.findById(eq(despesaRequestDTO.idCategoria()))).thenReturn(Optional.of(categoria));

        Despesa despesaExistente = new Despesa(usuario, categoria, "teste", 10.00, new Date());
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        despesaService.editarDespesa(idDespesa, despesaRequestDTO, usuario);

        verify(despesaRepository, times(1)).findById(idDespesa);
        verify(categoriaDespesaRepository, times(1)).findById(eq(despesaRequestDTO.idCategoria()));

        ArgumentCaptor<Despesa> despesaCaptor = ArgumentCaptor.forClass(Despesa.class);
        verify(despesaRepository, times(1)).save(despesaCaptor.capture());

        Despesa despesaEditada = despesaCaptor.getValue();
        assertEquals(categoria, despesaEditada.getCategoria(), "A categoria da despesa deve ser a mesma da categoria mockada");
    }

    @Test
    void testEditarDespesa_CategoriaNaoEncontrada() {
        Long idDespesa = 1L;
        Long idCategoriaNaoExistente = 999L;
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(idCategoriaNaoExistente, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(new Despesa()));

        when(categoriaDespesaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DespesaException.CategoriaDespesaNaoEncontradaById.class, () -> despesaService.editarDespesa(idDespesa, requestDTO, usuario));

        verify(despesaRepository, times(1)).findById(idDespesa);
        verify(categoriaDespesaRepository, times(1)).findById(idCategoriaNaoExistente);
        verify(despesaRepository, never()).save(any());
    }

    @Test
    void testEditarDespesa_DespesaNaoEncontrada() {
        Long idDespesaNaoExistente = 999L;
        Long idCategoria = 1L;
        DespesaRequestDTO requestDTO = new DespesaRequestDTO(idCategoria, "Descrição da despesa", 100.0, new Date());
        Usuario usuario = new Usuario();

        when(despesaRepository.findById(idDespesaNaoExistente)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaById.class, () -> despesaService.editarDespesa(idDespesaNaoExistente, requestDTO, usuario));

        verify(despesaRepository, times(1)).findById(idDespesaNaoExistente);
        verify(categoriaDespesaRepository, never()).findById(any());
        verify(despesaRepository, never()).save(any());
    }

    @Test
    public void testExcluirDespesa() {
        Long idDespesa = 1L;

        Usuario usuario = new Usuario("username", "senha", new Date());
        CategoriaDespesa categoria = new CategoriaDespesa("categoria teste");

        Despesa despesaExistente = new Despesa(usuario, categoria, "teste", 10.00, new Date());
        when(despesaRepository.findById(idDespesa)).thenReturn(Optional.of(despesaExistente));

        despesaService.excluirDespesa(idDespesa);

        verify(despesaRepository, times(1)).findById(idDespesa);
        verify(despesaRepository, times(1)).delete(despesaExistente);
    }

    @Test
    void testExcluirDespesa_DespesaNaoEncontrada() {
        Long idDespesaNaoExistente = 999L;

        when(despesaRepository.findById(idDespesaNaoExistente)).thenReturn(Optional.empty());

        assertThrows(DespesaException.DespesaNaoEncontradaById.class, () -> despesaService.excluirDespesa(idDespesaNaoExistente));

        verify(despesaRepository, never()).delete(any());
    }
}