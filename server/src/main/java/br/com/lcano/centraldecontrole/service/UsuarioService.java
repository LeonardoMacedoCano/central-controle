package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    @Transactional
    public void register(String username, String senha) {
        Usuario novoUsuario = new Usuario(username, senha, DateUtil.getDataAtual());
        this.usuarioRepository.save(novoUsuario);
        this.usuarioConfigService.createUsuarioConfig(novoUsuario);
    }

    public UserDetails findByUsername(String username) {
        return this.usuarioRepository.findByUsername(username);
    }

    public Usuario findUsuarioByUsername(String username) {
        return this.usuarioRepository.findUsuarioByUsername(username);
    }
}
