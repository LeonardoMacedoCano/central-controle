package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaFormaPagamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioConfigServiceTest {
    @Mock
    private UsuarioConfigRepository usuarioConfigRepository;
    @InjectMocks
    private UsuarioConfigService usuarioConfigService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioConfigService = new UsuarioConfigService(usuarioConfigRepository);
    }

    @Test
    void testGetUsuarioConfigById_Exists() {
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setId(1L);
        when(usuarioConfigRepository.findById(1L)).thenReturn(Optional.of(usuarioConfig));

        UsuarioConfig retrievedUsuarioConfig = usuarioConfigService.getUsuarioConfigById(1L);

        assertEquals(usuarioConfig, retrievedUsuarioConfig);
    }

    @Test
    void testGetUsuarioConfigById_NotExists() {
        when(usuarioConfigRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioException.UsuarioConfigNaoEncontradoById.class, () -> usuarioConfigService.getUsuarioConfigById(1L));
    }

    @Test
    void testGetUsuarioConfigByUsuario_UsuarioConfigFound() {
        Usuario usuario = new Usuario(1L, "Usuario1", "senha1", new Date(), true);
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setUsuario(usuario);

        when(usuarioConfigRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(usuarioConfig));

        UsuarioConfig result = usuarioConfigService.getUsuarioConfigByIdUsuario(usuario.getId());

        assertEquals(usuarioConfig, result);
    }

    @Test
    void testGetUsuarioConfigByUsuario_UsuarioConfigNotFound() {
        Usuario usuario = new Usuario(1L, "Usuario1", "senha1", new Date(), true);

        when(usuarioConfigRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.empty());

        assertThrows(UsuarioException.UsuarioConfigNaoEncontrado.class, () -> usuarioConfigService.getUsuarioConfigByIdUsuario(usuario.getId()));
    }

    @Test
    void testCreateAndSaveUsuarioConfig() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuarioConfigService.createAndSaveUsuarioConfig(usuario);

        verify(usuarioConfigRepository, times(1)).save(any(UsuarioConfig.class));
    }

    @Test
    void testEditarUsuarioConfig() {
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setId(1L);
        UsuarioConfigDTO data = new UsuarioConfigDTO();
        data.setDespesaNumeroMaxItemPagina(20);
        data.setDespesaValorMetaMensal(100.0);
        data.setDespesaDiaPadraoVencimento(15);
        DespesaFormaPagamento formaPagamento = new DespesaFormaPagamento();
        data.setDespesaFormaPagamentoPadrao(formaPagamento);

        when(usuarioConfigRepository.findById(1L)).thenReturn(Optional.of(usuarioConfig));

        usuarioConfigService.editarUsuarioConfig(1L, data);

        assertEquals(20, usuarioConfig.getDespesaNumeroMaxItemPagina());
        assertEquals(100.0, usuarioConfig.getDespesaValorMetaMensal());
        assertEquals(15, usuarioConfig.getDespesaDiaPadraoVencimento());
        assertEquals(formaPagamento, usuarioConfig.getDespesaFormaPagamentoPadrao());
    }
}
