package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> getTodosUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
    }
}
