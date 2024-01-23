package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.IdeiaRequestDTO;
import com.backend.centraldecontrole.dto.IdeiaResponseDTO;
import com.backend.centraldecontrole.model.*;
import com.backend.centraldecontrole.repository.CategoriaIdeiaRepository;
import com.backend.centraldecontrole.repository.IdeiaRepository;
import com.backend.centraldecontrole.util.CustomException;
import com.backend.centraldecontrole.util.MensagemConstantes;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    void testAdicionarIdeia() {
        IdeiaRequestDTO requestDTO = new IdeiaRequestDTO(1L, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        CategoriaIdeia categoriaIdeia = new CategoriaIdeia();
        when(categoriaIdeiaRepository.findById(1L)).thenReturn(Optional.of(categoriaIdeia));

        ResponseEntity<Object> response = IdeiaService.adicionarIdeia(requestDTO, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Map.of("success", MensagemConstantes.IDEIA_ADICIONADA_COM_SUCESSO), response.getBody());

        verify(IdeiaRepository, times(1)).save(any());
    }

    @Test
    void testAdicionarIdeia_CategoriaNaoEncontrada() {
        Long idCategoriaNaoExistente = 999L;
        IdeiaRequestDTO data = new IdeiaRequestDTO(idCategoriaNaoExistente, "Titulo", "Descricao", new Date(), Boolean.FALSE);
        Usuario usuario = new Usuario();

        CustomException.CategoriaIdeiaNaoEncontradaComIdException exception = assertThrows(
                CustomException.CategoriaIdeiaNaoEncontradaComIdException.class,
                () -> IdeiaService.adicionarIdeia(data, usuario)
        );

        assertEquals(String.format(MensagemConstantes.CATEGORIA_IDEIA_NAO_ENCONTRADA_COM_ID, idCategoriaNaoExistente), exception.getMessage());
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
    void testListarIdeiasDoUsuario_UsuarioNaoEncontrado() {
        Long idUsuario = null;
        int anoFiltro = 2024;
        int mesFiltro = 1;

        assertThrows(CustomException.UsuarioNaoEncontradoException.class,
                () -> IdeiaService.listarIdeiasDoUsuario(idUsuario, anoFiltro, mesFiltro));
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

        ResponseEntity<Object> response = IdeiaService.editarIdeia(idIdeia, IdeiaRequestDTO, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "O status HTTP deve ser OK");
        assertEquals(Map.of("success", MensagemConstantes.IDEIA_EDITADA_COM_SUCESSO), response.getBody());

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

        CustomException.CategoriaIdeiaNaoEncontradaComIdException exception = assertThrows(
                CustomException.CategoriaIdeiaNaoEncontradaComIdException.class,
                () -> IdeiaService.editarIdeia(idIdeia, requestDTO, usuario)
        );

        assertEquals(String.format(MensagemConstantes.CATEGORIA_IDEIA_NAO_ENCONTRADA_COM_ID, idCategoriaNaoExistente), exception.getMessage());

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

        CustomException.IdeiaNaoEncontradaComIdException exception = assertThrows(
                CustomException.IdeiaNaoEncontradaComIdException.class,
                () -> IdeiaService.editarIdeia(idIdeiaNaoExistente, requestDTO, usuario)
        );

        assertEquals(String.format(MensagemConstantes.IDEIA_NAO_ENCONTRADA_COM_ID, idIdeiaNaoExistente), exception.getMessage());

        verify(IdeiaRepository, times(1)).findById(idIdeiaNaoExistente);
        verify(categoriaIdeiaRepository, never()).findById(any());
        verify(IdeiaRepository, never()).save(any());
    }
}
