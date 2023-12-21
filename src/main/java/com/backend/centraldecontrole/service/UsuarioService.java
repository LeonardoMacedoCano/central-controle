package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.repository.UsuarioRepository;
import com.backend.centraldecontrole.dto.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> getTodosUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioResponseDTO::new).toList();
    }
}
