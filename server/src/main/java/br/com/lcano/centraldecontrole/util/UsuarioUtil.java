package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUtil {

    @Autowired
    private UsuarioService usuarioService;

    public Usuario getUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return usuarioService.findUsuarioByUsername(username);
        } else if (principal instanceof String) {
            return usuarioService.findUsuarioByUsername((String) principal);
        } else {
            throw new IllegalStateException("Tipo de Principal não suportado: " + principal.getClass().getName());
        }
    }
}
