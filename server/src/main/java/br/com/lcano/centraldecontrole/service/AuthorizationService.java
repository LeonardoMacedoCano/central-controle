package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final DateUtil dateUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioRepository.findByUsername(username);
    }

    public Usuario findUsuarioByUsername(String username) {
        return this.usuarioRepository.findUsuarioByUsername(username);
    }

    public boolean usuarioJaCadastrado(String username) {
        return this.usuarioRepository.findUsuarioByUsername(username) != null;
    }

    public boolean usuarioAtivo(String username) {
        Usuario usuario = this.usuarioRepository.findUsuarioByUsername(username);
        return usuario != null && usuario.isEnabled();
    }

    public LoginDTO login(UsuarioDTO data, AuthenticationManager authenticationManager) {
        if (!this.usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioNaoEncontrado();
        }
        if (!this.usuarioAtivo(data.getUsername())) {
            throw new UsuarioException.UsuarioDesativado();
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getSenha());
        var auth = authenticationManager.authenticate(usernamePassword);
        Usuario usuario = (Usuario) auth.getPrincipal();
        String token = tokenService.gerarToken(usuario);

        return new LoginDTO(
                usuario.getUsername(),
                token,
                usuario.getTema() != null ? usuario.getTema().getId() : null,
                usuario.getArquivo() != null ? usuario.getArquivo().getId() : null
        );
    }

    @Transactional
    public void register(UsuarioDTO data) {
        if (this.usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioJaCadastrado();
        }
        Usuario novoUsuario = new Usuario(data.getUsername(), new BCryptPasswordEncoder().encode(data.getSenha()), dateUtil.getDataAtual());
        this.usuarioRepository.save(novoUsuario);
    }

    public LoginDTO validateToken(String token) {
        String username = tokenService.validateToken(token);
        Usuario usuario = usuarioRepository.findUsuarioByUsername(username);
        return new LoginDTO(
                usuario.getUsername(),
                token,
                usuario.getTema() != null ? usuario.getTema().getId() : null,
                usuario.getArquivo() != null ? usuario.getArquivo().getId() : null
        );
    }
}

