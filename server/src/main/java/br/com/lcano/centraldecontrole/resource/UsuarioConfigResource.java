package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.service.UsuarioConfigService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarioconfig")
public class UsuarioConfigResource {
    @Autowired
    private final UsuarioConfigService usuarioConfigService;

    public UsuarioConfigResource(UsuarioConfigService usuarioConfigService) {
        this.usuarioConfigService = usuarioConfigService;
    }

    @PutMapping("/{idUsuarioConfig}")
    public ResponseEntity<Object> editarUsuarioConfig(@PathVariable Long idUsuarioConfig, @RequestBody UsuarioConfigDTO data) {
        usuarioConfigService.editarUsuarioConfig(idUsuarioConfig, data);
        return CustomSuccess.buildResponseEntity("Configuração editada com sucesso.");
    }

    @GetMapping("/{idUsuarioConfig}")
    public ResponseEntity<UsuarioConfigDTO> getUsuarioConfigByIdWith(@PathVariable Long idUsuarioConfig) {
        return ResponseEntity.ok(UsuarioConfigDTO.converterParaDTO(usuarioConfigService.getUsuarioConfigById(idUsuarioConfig)));
    }
}
