package br.com.lcano.centraldecontrole.exception;

import br.com.lcano.centraldecontrole.exception.fluxocaixa.ExtratoException;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.FluxoCaixaConfigException;
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

    @ExceptionHandler({UsuarioException.CredenciaisInvalidas.class})
    protected ResponseEntity<Object> handleCredenciaisInvalidas(UsuarioException.CredenciaisInvalidas ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
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

    @ExceptionHandler({UsuarioException.SenhaAtualIncorreta.class})
    protected ResponseEntity<Object> handleSenhaAtualIncorreta(UsuarioException.SenhaAtualIncorreta ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
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

    @ExceptionHandler({FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada.class})
    protected ResponseEntity<Object> handleCategoriaPadraoNaoEncontrada(FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({FluxoCaixaConfigException.ParametroNaoEncontrado.class})
    protected ResponseEntity<Object> handleParametroNaoEncontrado(FluxoCaixaConfigException.ParametroNaoEncontrado ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ExtratoException.ErroPreenchimentoCampo.class})
    protected ResponseEntity<Object> handleErroPreenchimentoCampo(ExtratoException.ErroPreenchimentoCampo ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}