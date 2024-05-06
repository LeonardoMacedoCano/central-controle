package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConfigService usuarioConfigService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioConfigService usuarioConfigService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConfigService = usuarioConfigService;
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> getTodosUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
    }

    @Transactional
    public void gerarUsuario(String username, String senha) {
        Usuario novoUsuario = new Usuario(username, senha, DateUtil.getDataAtual());
        salvarUsuario(novoUsuario);
        usuarioConfigService.gerarUsuarioConfig(novoUsuario);
    }
}
