package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.service.UsuarioService;
import com.backend.centraldecontrole.dto.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/getTodosUsuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> getTodosUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}
