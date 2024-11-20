package br.com.lcano.centraldecontrole.exception;

import br.com.lcano.centraldecontrole.exception.fluxocaixa.AtivoException;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.ReceitaException;
import br.com.lcano.centraldecontrole.exception.servicos.DockerException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno no servidor: ";

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("error", mensagem));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, MSG_ERRO_GENERICO + ex);
    }

    @ExceptionHandler({BadCredentialsException.class, JWTVerificationException.class})
    protected ResponseEntity<Object> handleInvalidCredentials() {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, UsuarioException.MSG_CREDENCIAIS_INVALIDAS);
    }

    @ExceptionHandler({UsuarioException.UsuarioJaCadastrado.class})
    protected ResponseEntity<Object> handleUsuarioJaCadastrado(UsuarioException.UsuarioJaCadastrado ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.UsuarioNaoEncontrado.class})
    protected ResponseEntity<Object> handleUsuarioNaoEncontrado(UsuarioException.UsuarioNaoEncontrado ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.UsuarioDesativado.class})
    protected ResponseEntity<Object> handleUsuarioDesativado(UsuarioException.UsuarioDesativado ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.ErroGerarToken.class})
    protected ResponseEntity<Object> handleErroGerarToken(UsuarioException.ErroGerarToken ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.TokenExpiradoOuInvalido.class})
    protected ResponseEntity<Object> handleTokenExpiradoOuInvalido(UsuarioException.TokenExpiradoOuInvalido ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.UsuarioConfigNaoEncontradoById.class})
    protected ResponseEntity<Object> handleUsuarioConfigNaoEncontradoById(UsuarioException.UsuarioConfigNaoEncontradoById ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({UsuarioException.UsuarioConfigNaoEncontrado.class})
    protected ResponseEntity<Object> handleUsuarioConfigNaoEncontrado(UsuarioException.UsuarioConfigNaoEncontrado ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({DespesaException.CategoriaNaoEncontradaById.class})
    protected ResponseEntity<Object> handleDespesaCategoriaNaoEncontradaById(DespesaException.CategoriaNaoEncontradaById ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({DespesaException.DespesaNaoEncontradaByLancamentoId.class})
    protected ResponseEntity<Object> handleDespesaNaoEncontradaByLancamentoId(DespesaException.DespesaNaoEncontradaByLancamentoId ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ReceitaException.CategoriaNaoEncontradaById.class})
    protected ResponseEntity<Object> handleReceitaCategoriaNaoEncontradaById(ReceitaException.CategoriaNaoEncontradaById ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ReceitaException.ReceitaNaoEncontradaByLancamentoId.class})
    protected ResponseEntity<Object> handleReceitaNaoEncontradaByLancamentoId(ReceitaException.ReceitaNaoEncontradaByLancamentoId ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({AtivoException.CategoriaNaoEncontradaById.class})
    protected ResponseEntity<Object> handleAtivoCategoriaNaoEncontradaById(AtivoException.CategoriaNaoEncontradaById ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({AtivoException.AtivoNaoEncontradaByLancamentoId.class})
    protected ResponseEntity<Object> handleAtivoNaoEncontradaByLancamentoId(AtivoException.AtivoNaoEncontradaByLancamentoId ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({LancamentoException.LancamentoNaoEncontradoById.class})
    protected ResponseEntity<Object> handleLancamentoNaoEncontradaById(LancamentoException.LancamentoNaoEncontradoById ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({LancamentoException.LancamentoTipoNaoSuportado.class})
    protected ResponseEntity<Object> handleLancamentoTipoNaoSuportado(LancamentoException.LancamentoTipoNaoSuportado ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({LancamentoException.ErroIniciarImportacaoExtrato.class})
    protected ResponseEntity<Object> handleErroIniciarImportacaoExtrato(LancamentoException.ErroIniciarImportacaoExtrato ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({ArquivoException.ArquivoJaImportado.class})
    protected ResponseEntity<Object> handleArquivoJaImportado(ArquivoException.ArquivoJaImportado ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({ArquivoException.ArquivoNaoEncontrado.class})
    protected ResponseEntity<Object> handleArquivoNaoEncontrado(ArquivoException.ArquivoNaoEncontrado ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({DockerException.DockerNaoEncontradoByName.class})
    protected ResponseEntity<Object> handleDockerNaoEncontradoByName(DockerException.DockerNaoEncontradoByName ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({DockerException.DockerErroAlterarStatus.class})
    protected ResponseEntity<Object> handleDockerErroAlterarStatus(DockerException.DockerErroAlterarStatus ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}