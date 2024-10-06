package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioService.findByUsername(username);
    }

    public boolean usuarioJaCadastrado(String username) {
        return this.loadUserByUsername(username) != null;
    }

    public boolean usuarioAtivo(String username) {
        return this.loadUserByUsername(username).isEnabled();
    }

    public LoginDTO login(UsuarioDTO data, AuthenticationManager authenticationManager) {
        if (!this.usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioNaoEncontrado();
        } else if (!this.usuarioAtivo(data.getUsername())) {
            throw new UsuarioException.UsuarioDesativado();
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getSenha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return new LoginDTO(data.getUsername(), token);
    }

    public void register(UsuarioDTO data) {
        if (this.usuarioJaCadastrado(data.getUsername())) throw new UsuarioException.UsuarioJaCadastrado();
        this.usuarioService.register(data.getUsername(), new BCryptPasswordEncoder().encode(data.getSenha()));
    }

    public LoginDTO validateToken(String token) {
        String username = this.tokenService.validateToken(token);
        UserDetails usuario = this.usuarioRepository.findByUsername(username);
        return new LoginDTO(usuario.getUsername(), token);
    }
}
