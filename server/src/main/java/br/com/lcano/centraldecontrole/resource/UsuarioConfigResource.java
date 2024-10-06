package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.service.UsuarioConfigService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/usuarioconfig")
public class UsuarioConfigResource {
    @Autowired
    private final UsuarioConfigService usuarioConfigService;

    @PutMapping("/{idUsuarioConfig}")
    public ResponseEntity<Object> editarUsuarioConfig(@PathVariable Long idUsuarioConfig, @RequestBody UsuarioConfigDTO data) {
        usuarioConfigService.editarUsuarioConfig(idUsuarioConfig, data);
        return CustomSuccess.buildResponseEntity("Configuração editada com sucesso.");
    }

    @GetMapping()
    public ResponseEntity<UsuarioConfigDTO> getUsuarioConfigByUsuario(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return ResponseEntity.ok(UsuarioConfigDTO.converterParaDTO(usuarioConfigService.getUsuarioConfigByIdUsuario(usuario.getId())));
    }
}
