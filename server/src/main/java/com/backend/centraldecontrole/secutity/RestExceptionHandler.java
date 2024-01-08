package com.backend.centraldecontrole.secutity;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backend.centraldecontrole.util.CustomException;
import com.backend.centraldecontrole.util.MensagemConstantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("error", mensagem));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, MensagemConstantes.ERRO_GENERICO);
    }

    @ExceptionHandler({BadCredentialsException.class, JWTVerificationException.class})
    protected ResponseEntity<Object> handleInvalidCredentials(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, MensagemConstantes.CREDENCIAIS_INVALIDAS);
    }

    @ExceptionHandler({CustomException.UsuarioJaCadastradoException.class})
    protected ResponseEntity<Object> handleUsuarioJaCadastrado(CustomException.UsuarioJaCadastradoException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({CustomException.UsuarioNaoEncontradoException.class})
    protected ResponseEntity<Object> handleUsuarioNaoEncontradoException(CustomException.UsuarioNaoEncontradoException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({CustomException.GerarTokenException.class})
    protected ResponseEntity<Object> handleGerarTokenException(CustomException.GerarTokenException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({CustomException.TokenExpiradoOuInvalidoException.class})
    protected ResponseEntity<Object> handleTokenExpiradoOuInvalidoException(CustomException.TokenExpiradoOuInvalidoException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({CustomException.CategoriaDespesaNaoEncontradaComIdException.class})
    protected ResponseEntity<Object> handleCategoriaDespesaNaoEncontradaComIdException(CustomException.CategoriaDespesaNaoEncontradaComIdException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({CustomException.DespesaNaoEncontradaComIdException.class})
    protected ResponseEntity<Object> handleDespesaNaoEncontradaComIdException(CustomException.DespesaNaoEncontradaComIdException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({CustomException.CategoriaTarefaNaoEncontradaComIdException.class})
    protected ResponseEntity<Object> handleCategoriaTarefaNaoEncontradaComIdException(CustomException.CategoriaTarefaNaoEncontradaComIdException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({CustomException.TarefaNaoEncontradaComIdException.class})
    protected ResponseEntity<Object> handleTarefaNaoEncontradaComIdException(CustomException.TarefaNaoEncontradaComIdException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}