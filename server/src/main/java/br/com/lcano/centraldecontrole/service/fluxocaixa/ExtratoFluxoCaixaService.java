package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomovimentacaob3.ImportacaoExtratoMovimentacaoB3JobStarter;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratocontacorrente.ImportacaoExtratoContaCorrenteJobStarter;
import br.com.lcano.centraldecontrole.batch.fluxocaixa.extratofaturacartao.ImportacaoExtratoFaturaCartaoJobStarter;
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
    private final ImportacaoExtratoFaturaCartaoJobStarter importacaoExtratoFaturaCartaoJobStarter;
    private final ImportacaoExtratoContaCorrenteJobStarter importacaoExtratoContaCorrenteJobStarter;
    private final ImportacaoExtratoMovimentacaoB3JobStarter importacaoExtratoMovimentacaoB3JobStarter;

    public void importExtratoFaturaCartao(MultipartFile file, Date dataVencimento) throws Exception {
        fluxoCaixaParametroService.validateParametro();
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoFaturaCartaoJobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId(), dataVencimento);
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

    public void importExtratoMovimentacaoB3(MultipartFile file) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivo(file);

        try {
            importacaoExtratoMovimentacaoB3JobStarter.startJob(arquivo.getId(), usuarioUtil.getUsuarioAutenticado().getId());
        } catch (Exception e) {
            arquivoService.deleteArquivoIfExists(arquivo.getId());
            throw new LancamentoException.ErroIniciarImportacaoExtrato(e);
        }
    }
}
