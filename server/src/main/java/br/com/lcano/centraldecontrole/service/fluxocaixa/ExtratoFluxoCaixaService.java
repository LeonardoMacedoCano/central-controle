package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratoativosb3.ImportacaoExtratoAtivosB3JobStarter;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente.ImportacaoExtratoContaCorrenteJobStarter;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomensalcartao.ImportacaoExtratoMensalCartaoJobStarter;
import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.exception.LancamentoException;
import br.com.lcano.centraldecontrole.service.ArquivoService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@AllArgsConstructor
public class ExtratoFluxoCaixaService {
    private final UsuarioUtil usuarioUtil;
    private final ArquivoService arquivoService;
    private final FluxoCaixaParametroService fluxoCaixaParametroService;
    private final ImportacaoExtratoMensalCartaoJobStarter importacaoExtratoMensalCartaoJobStarter;
    private final ImportacaoExtratoContaCorrenteJobStarter importacaoExtratoContaCorrenteJobStarter;
    private final ImportacaoExtratoAtivosB3JobStarter importacaoExtratoAtivosB3JobStarter;

    public void importExtratoMensalCartao(MultipartFile file, Date dataVencimento) throws Exception {
        fluxoCaixaParametroService.validateParametro();
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoMensalCartaoJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId(), dataVencimento);
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }

    public void importExtratoContaCorrente(MultipartFile file) throws Exception {
        fluxoCaixaParametroService.validateParametro();
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoContaCorrenteJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId());
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }

    public void importExtratoAtivosB3(MultipartFile file) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoAtivosB3JobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId());
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }
}
