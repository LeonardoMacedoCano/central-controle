package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConfigService usuarioConfigService;
    private final DateUtil dateUtil;

    @Transactional
    public void register(String username, String senha) {
        Usuario novoUsuario = new Usuario(username, senha, dateUtil.getDataAtual());
        this.usuarioRepository.save(novoUsuario);
        this.usuarioConfigService.createAndSaveUsuarioConfig(novoUsuario);
    }

    public UserDetails findByUsername(String username) {
        return this.usuarioRepository.findByUsername(username);
    }

    public Usuario findUsuarioByUsername(String username) {
        return this.usuarioRepository.findUsuarioByUsername(username);
    }

    public Usuario getUsuarioById(Long id) {
        return this.usuarioRepository.findById(id)
                .orElseThrow(UsuarioException.UsuarioNaoEncontrado::new);
    }
}
