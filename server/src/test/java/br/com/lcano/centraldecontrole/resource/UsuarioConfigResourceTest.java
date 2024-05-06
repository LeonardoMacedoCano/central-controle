package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.service.UsuarioConfigService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioConfigResourceTest {
    @Mock
    private UsuarioConfigService usuarioConfigService;

    @InjectMocks
    private UsuarioConfigResource usuarioConfigResource;

    public UsuarioConfigResourceTest() {
        MockitoAnnotations.openMocks(this);
        usuarioConfigResource = new UsuarioConfigResource(usuarioConfigService);
    }

    @Test
    void testEditarUsuarioConfig() {
        UsuarioConfigDTO data = new UsuarioConfigDTO();
        ResponseEntity<Object> responseEntity = usuarioConfigResource.editarUsuarioConfig(1L, data);
        verify(usuarioConfigService, times(1)).editarUsuarioConfig(1L, data);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetUsuarioConfigByIdWith() {
        UsuarioConfig usuarioConfig = new UsuarioConfig();
        usuarioConfig.setId(1L);
        usuarioConfig.setDespesaNumeroItemPagina(10);
        usuarioConfig.setDespesaValorMetaMensal(100.0);
        usuarioConfig.setDespesaDiaPadraoVencimento(15);

        UsuarioConfigDTO usuarioConfigDTO = UsuarioConfigDTO.converterParaDTO(usuarioConfig);

        when(usuarioConfigService.getUsuarioConfigById(1L)).thenReturn(usuarioConfig);

        ResponseEntity<UsuarioConfigDTO> responseEntity = usuarioConfigResource.getUsuarioConfigByIdWith(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuarioConfigDTO, responseEntity.getBody());
    }
}
