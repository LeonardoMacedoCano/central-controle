package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
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
    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public void cadastrarUsuario(UsuarioDTO data) {
        if (usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioJaCadastrado();
        }
        usuarioService.gerarUsuario(data.getUsername(), new BCryptPasswordEncoder().encode(data.getSenha()));
    }

    public boolean usuarioJaCadastrado(String username) {
        return loadUserByUsername(username) != null;
    }

    public boolean usuarioAtivo(String username) {
        return loadUserByUsername(username).isEnabled();
    }
}