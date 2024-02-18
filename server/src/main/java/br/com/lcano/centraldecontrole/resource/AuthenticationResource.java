package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.service.TokenService;
import br.com.lcano.centraldecontrole.dto.LoginResponseDTO;
import br.com.lcano.centraldecontrole.dto.UsuarioRequestDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioRequestDTO data) {
        if (!authorizationService.usuarioJaCadastrado(data.username())) {
            throw new UsuarioException.UsuarioNaoEncontrado();
        } else if (!authorizationService.usuarioAtivo(data.username())) {
            throw new UsuarioException.UsuarioDesativado();
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        var loginResponse = new LoginResponseDTO(
            ((Usuario) auth.getPrincipal()).getUsername(),
            token
        );

        var usuarioDTO = loginResponse.usuario();

        var responseBody = Map.of(
            "usuario", Map.of(
                "username", usuarioDTO.username(),
                "token", usuarioDTO.token()
            )
        );

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity cadastrarUsuario(@RequestBody UsuarioRequestDTO data){
        authorizationService.cadastrarUsuario(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Object> validateToken(@RequestParam String token) {
        var usernameToken = tokenService.validateToken(token);
        UserDetails usuario = usuarioRepository.findByUsername(usernameToken);
        var loginResponse = new LoginResponseDTO(usuario.getUsername(), token);
        var usuarioDTO = loginResponse.usuario();

        var responseBody = Map.of(
            "usuario", Map.of(
                "username", usuarioDTO.username(),
                "token", usuarioDTO.token()
            )
        );

        return ResponseEntity.ok().body(responseBody);
    }
}
