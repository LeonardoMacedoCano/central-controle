package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioResponseDTO;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getTodosUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}
