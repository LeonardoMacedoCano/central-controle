package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public void cadastrarUsuario(UsuarioDTO data) {
        if (usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioJaCadastrado();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        Usuario novoUsuario = new Usuario(data.getUsername(), encryptedPassword, DateUtil.getDataAtual());
        this.usuarioRepository.save(novoUsuario);
    }

    public boolean usuarioJaCadastrado(String username) {
        return loadUserByUsername(username) != null;
    }

    public boolean usuarioAtivo(String username) {
        return loadUserByUsername(username).isEnabled();
    }
}