package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.TarefaRequestDTO;
import br.com.lcano.centraldecontrole.dto.TarefaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaTarefa;
import br.com.lcano.centraldecontrole.domain.Tarefa;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.TarefaException;
import br.com.lcano.centraldecontrole.repository.CategoriaTarefaRepository;
import br.com.lcano.centraldecontrole.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TarefaServiceTest {
    @Mock
    private TarefaRepository tarefaRepository;
    @Mock
    private CategoriaTarefaRepository categoriaTarefaRepository;
    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void testGerarTarefa() {
        TarefaRequestDTO requestDTO = new TarefaRequestDTO(1L, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        CategoriaTarefa categoriaTarefa = new CategoriaTarefa();
        when(categoriaTarefaRepository.findById(1L)).thenReturn(Optional.of(categoriaTarefa));

        tarefaService.gerarTarefa(requestDTO, usuario);

        verify(tarefaRepository, times(1)).save(any());
    }

    @Test
    void testGerarTarefa_CategoriaNaoEncontrada() {
        Long idCategoriaNaoExistente = 999L;
        TarefaRequestDTO data = new TarefaRequestDTO(idCategoriaNaoExistente, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        assertThrows(TarefaException.CategoriaTarefaNaoEncontradaById.class, () -> tarefaService.gerarTarefa(data, usuario));
    }

    @Test
    public void testListarTarefasDoUsuario() {
        int anoFiltro = 2024;
        int mesFiltro = 1;
        LocalDate primeiroDiaDoMes = LocalDate.of(anoFiltro, mesFiltro, 1);
        LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
        Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Usuario usuario = new Usuario("usuario teste", "senha123", new Date());
        usuario.setId(1L);
        CategoriaTarefa categoriaTarefa = new CategoriaTarefa("Categoria teste");

        Tarefa tarefa1 = new Tarefa(usuario, categoriaTarefa, "titulo 1", "tarefa 1", new Date(), false);
        Tarefa tarefa2 = new Tarefa(usuario, categoriaTarefa, "titulo 2", "tarefa 2", new Date(), false);

        List<Tarefa> listaTarefas = Arrays.asList(tarefa1, tarefa2);

        when(tarefaRepository.findByUsuarioIdAndDataPrazoBetween(usuario.getId(), dataInicio, dataFim)).thenReturn(listaTarefas);

        List<TarefaResponseDTO> resultado = tarefaService.listarTarefasDoUsuario(usuario.getId(), anoFiltro, mesFiltro);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        TarefaResponseDTO tarefaResponseDTO1 = resultado.get(0);
        assertEquals(tarefa1.getId(), tarefaResponseDTO1.id());
        assertEquals(tarefa1.getDescricao(), tarefaResponseDTO1.descricao());

        TarefaResponseDTO tarefaResponseDTO2 = resultado.get(1);
        assertEquals(tarefa2.getId(), tarefaResponseDTO2.id());
        assertEquals(tarefa2.getDescricao(), tarefaResponseDTO2.descricao());
    }

    @Test
    public void testEditarTarefa() {
        Long idTarefa = 1L;
        TarefaRequestDTO tarefaRequestDTO = new TarefaRequestDTO(1L, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario("username", "senha", new Date());

        CategoriaTarefa categoria = new CategoriaTarefa("categoria teste");
        when(categoriaTarefaRepository.findById(eq(tarefaRequestDTO.idCategoria()))).thenReturn(Optional.of(categoria));

        Tarefa tarefaExistente = new Tarefa(usuario, categoria, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefaExistente));

        tarefaService.editarTarefa(idTarefa, tarefaRequestDTO, usuario);

        verify(tarefaRepository, times(1)).findById(idTarefa);
        verify(categoriaTarefaRepository, times(1)).findById(eq(tarefaRequestDTO.idCategoria()));

        ArgumentCaptor<Tarefa> tarefaCaptor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaRepository, times(1)).save(tarefaCaptor.capture());

        Tarefa tarefaEditada = tarefaCaptor.getValue();
        assertEquals(categoria, tarefaEditada.getCategoria(), "A categoria da tarefa deve ser a mesma da categoria mockada");
    }

    @Test
    void testEditarTarefa_CategoriaNaoEncontrada() {
        Long idTarefa = 1L;
        Long idCategoriaNaoExistente = 999L;
        TarefaRequestDTO requestDTO = new TarefaRequestDTO(idCategoriaNaoExistente, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(new Tarefa()));

        when(categoriaTarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TarefaException.CategoriaTarefaNaoEncontradaById.class, () -> tarefaService.editarTarefa(idTarefa, requestDTO, usuario));

        verify(tarefaRepository, times(1)).findById(idTarefa);
        verify(categoriaTarefaRepository, times(1)).findById(idCategoriaNaoExistente);
        verify(tarefaRepository, never()).save(any());
    }

    @Test
    void testEditarTarefa_TarefaNaoEncontrada() {
        Long idTarefaNaoExistente = 999L;
        Long idCategoria = 1L;
        TarefaRequestDTO requestDTO = new TarefaRequestDTO(idCategoria, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        when(tarefaRepository.findById(idTarefaNaoExistente)).thenReturn(Optional.empty());

        assertThrows(TarefaException.TarefaNaoEncontradaById.class, () -> tarefaService.editarTarefa(idTarefaNaoExistente, requestDTO, usuario));

        verify(tarefaRepository, times(1)).findById(idTarefaNaoExistente);
        verify(categoriaTarefaRepository, never()).findById(any());
        verify(tarefaRepository, never()).save(any());
    }
}
