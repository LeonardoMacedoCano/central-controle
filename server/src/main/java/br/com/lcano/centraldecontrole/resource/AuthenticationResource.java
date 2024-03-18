package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.service.TokenService;
import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AuthorizationService authorizationService;
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public AuthenticationResource(AuthenticationManager authenticationManager,
                                  AuthorizationService authorizationService,
                                  TokenService tokenService,
                                  UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody UsuarioDTO data) {
        if (!authorizationService.usuarioJaCadastrado(data.getUsername())) {
            throw new UsuarioException.UsuarioNaoEncontrado();
        } else if (!authorizationService.usuarioAtivo(data.getUsername())) {
            throw new UsuarioException.UsuarioDesativado();
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        LoginDTO responseDTO = new LoginDTO(data.getUsername(), token);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity cadastrarUsuario(@RequestBody UsuarioDTO data){
        authorizationService.cadastrarUsuario(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validateToken")
    public ResponseEntity<LoginDTO> validateToken(@RequestParam String token) {
        var usernameToken = tokenService.validateToken(token);
        UserDetails usuario = usuarioRepository.findByUsername(usernameToken);
        LoginDTO responseDTO = new LoginDTO(usuario.getUsername(), token);

        return ResponseEntity.ok().body(responseDTO);
    }
}
