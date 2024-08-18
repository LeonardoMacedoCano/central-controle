package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.dto.LoginDTO;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {
    @Autowired
    private final AuthorizationService authorizationService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResource(AuthorizationService authorizationService,
                                  AuthenticationManager authenticationManager) {
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(this.authorizationService.login(usuarioDTO, this.authenticationManager));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UsuarioDTO data){
        this.authorizationService.register(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validateToken")
    public ResponseEntity<LoginDTO> validateToken(@RequestParam String token) {
        return ResponseEntity.ok().body(this.authorizationService.validateToken(token));
    }
}
