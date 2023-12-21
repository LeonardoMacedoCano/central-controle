package com.backend.centraldecontrole.secutity;

import com.backend.centraldecontrole.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.startsWith("/auth") && (path.length() == 5 || path.charAt(5) == '/')) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String usernameToken = tokenService.validateToken(token);

            if (usernameToken != null && !usernameToken.isEmpty()) {
                UserDetails usuario = usuarioRepository.findByUsername(usernameToken);
                request.setAttribute("usuario", usuario);

                return true;
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        return false;
    }
}