package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.IdeiaRequestDTO;
import br.com.lcano.centraldecontrole.dto.IdeiaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaIdeia;
import br.com.lcano.centraldecontrole.domain.Ideia;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.IdeiaException;
import br.com.lcano.centraldecontrole.repository.CategoriaIdeiaRepository;
import br.com.lcano.centraldecontrole.repository.IdeiaRepository;
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
public class IdeiaServiceTest {
    @Mock
    private IdeiaRepository IdeiaRepository;
    @Mock
    private CategoriaIdeiaRepository categoriaIdeiaRepository;
    @InjectMocks
    private IdeiaService IdeiaService;

    @Test
    void testGerarIdeia() {
        IdeiaRequestDTO requestDTO = new IdeiaRequestDTO(1L, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        CategoriaIdeia categoriaIdeia = new CategoriaIdeia();
        when(categoriaIdeiaRepository.findById(1L)).thenReturn(Optional.of(categoriaIdeia));

       IdeiaService.gerarIdeia(requestDTO, usuario);

        verify(IdeiaRepository, times(1)).save(any());
    }

    @Test
    void testGerarIdeia_CategoriaNaoEncontrada() {
        Long idCategoriaNaoExistente = 999L;
        IdeiaRequestDTO data = new IdeiaRequestDTO(idCategoriaNaoExistente, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        assertThrows(IdeiaException.CategoriaIdeiaNaoEncontradaById.class,() -> IdeiaService.gerarIdeia(data, usuario));
    }

    @Test
    public void testListarIdeiasDoUsuario() {
        int anoFiltro = 2024;
        int mesFiltro = 1;
        LocalDate primeiroDiaDoMes = LocalDate.of(anoFiltro, mesFiltro, 1);
        LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
        Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Usuario usuario = new Usuario("usuario teste", "senha123", new Date());
        usuario.setId(1L);
        CategoriaIdeia categoriaIdeia = new CategoriaIdeia("Categoria teste");

        Ideia Ideia1 = new Ideia(usuario, categoriaIdeia, "titulo 1", "Ideia 1", new Date(), false);
        Ideia Ideia2 = new Ideia(usuario, categoriaIdeia, "titulo 2", "Ideia 2", new Date(), false);

        List<Ideia> listaIdeias = Arrays.asList(Ideia1, Ideia2);

        when(IdeiaRepository.findByUsuarioIdAndDataPrazoBetween(usuario.getId(), dataInicio, dataFim)).thenReturn(listaIdeias);

        List<IdeiaResponseDTO> resultado = IdeiaService.listarIdeiasDoUsuario(usuario.getId(), anoFiltro, mesFiltro);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        IdeiaResponseDTO IdeiaResponseDTO1 = resultado.get(0);
        assertEquals(Ideia1.getId(), IdeiaResponseDTO1.id());
        assertEquals(Ideia1.getDescricao(), IdeiaResponseDTO1.descricao());

        IdeiaResponseDTO IdeiaResponseDTO2 = resultado.get(1);
        assertEquals(Ideia2.getId(), IdeiaResponseDTO2.id());
        assertEquals(Ideia2.getDescricao(), IdeiaResponseDTO2.descricao());
    }

    @Test
    public void testEditarIdeia() {
        Long idIdeia = 1L;
        IdeiaRequestDTO IdeiaRequestDTO = new IdeiaRequestDTO(1L, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario("username", "senha", new Date());

        CategoriaIdeia categoria = new CategoriaIdeia("categoria teste");
        when(categoriaIdeiaRepository.findById(eq(IdeiaRequestDTO.idCategoria()))).thenReturn(Optional.of(categoria));

        Ideia IdeiaExistente = new Ideia(usuario, categoria, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        when(IdeiaRepository.findById(idIdeia)).thenReturn(Optional.of(IdeiaExistente));

        IdeiaService.editarIdeia(idIdeia, IdeiaRequestDTO, usuario);

        verify(IdeiaRepository, times(1)).findById(idIdeia);
        verify(categoriaIdeiaRepository, times(1)).findById(eq(IdeiaRequestDTO.idCategoria()));

        ArgumentCaptor<Ideia> IdeiaCaptor = ArgumentCaptor.forClass(Ideia.class);
        verify(IdeiaRepository, times(1)).save(IdeiaCaptor.capture());

        Ideia IdeiaEditada = IdeiaCaptor.getValue();
        assertEquals(categoria, IdeiaEditada.getCategoria(), "A categoria da Ideia deve ser a mesma da categoria mockada");
    }

    @Test
    void testEditarIdeia_CategoriaNaoEncontrada() {
        Long idIdeia = 1L;
        Long idCategoriaNaoExistente = 999L;
        IdeiaRequestDTO requestDTO = new IdeiaRequestDTO(idCategoriaNaoExistente, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        when(IdeiaRepository.findById(idIdeia)).thenReturn(Optional.of(new Ideia()));

        when(categoriaIdeiaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IdeiaException.CategoriaIdeiaNaoEncontradaById.class, () -> IdeiaService.editarIdeia(idIdeia, requestDTO, usuario));

        verify(IdeiaRepository, times(1)).findById(idIdeia);
        verify(categoriaIdeiaRepository, times(1)).findById(idCategoriaNaoExistente);
        verify(IdeiaRepository, never()).save(any());
    }

    @Test
    void testEditarIdeia_IdeiaNaoEncontrada() {
        Long idIdeiaNaoExistente = 999L;
        Long idCategoria = 1L;
        IdeiaRequestDTO requestDTO = new IdeiaRequestDTO(idCategoria, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        when(IdeiaRepository.findById(idIdeiaNaoExistente)).thenReturn(Optional.empty());

        assertThrows(IdeiaException.IdeiaNaoEncontradaById.class,() -> IdeiaService.editarIdeia(idIdeiaNaoExistente, requestDTO, usuario));

        verify(IdeiaRepository, times(1)).findById(idIdeiaNaoExistente);
        verify(categoriaIdeiaRepository, never()).findById(any());
        verify(IdeiaRepository, never()).save(any());
    }
}
