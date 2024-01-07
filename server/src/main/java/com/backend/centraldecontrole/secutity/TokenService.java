package com.backend.centraldecontrole.secutity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.util.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("backend-centraldecontrole")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(getDataExpiracao())
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new CustomException.GerarTokenException();
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("backend-centraldecontrole")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new CustomException.TokenExpiradoOuInvalidoException();
        }
    }

    private Instant getDataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}