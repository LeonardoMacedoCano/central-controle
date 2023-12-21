package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.service.UsuarioService;
import com.backend.centraldecontrole.dto.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            List<UsuarioResponseDTO> usuarios = usuarioService.getTodosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
