package br.com.lcano.centraldecontrole.batch.fluxocaixa.extratomensalcartao;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoMensalCartaoDTO;
import br.com.lcano.centraldecontrole.service.ArquivoService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

@Component
@StepScope
public class ImportacaoExtratoMensalCartaoReader implements ItemReader<ExtratoMensalCartaoDTO> {

    private final ArquivoService arquivoService;
    private final Long arquivoId;
    private Iterator<ExtratoMensalCartaoDTO> extratoIterator;

    @Autowired
    public ImportacaoExtratoMensalCartaoReader(ArquivoService arquivoService,
                                               @Value("#{jobParameters['arquivoId']}") Long arquivoId) {
        this.arquivoService = arquivoService;
        this.arquivoId = arquivoId;
    }

    @Override
    public ExtratoMensalCartaoDTO read() throws Exception {
        if (extratoIterator == null) {
            this.initializeExtratoIterator();
        }
        return this.getNextExtrato();
    }

    private void initializeExtratoIterator() throws Exception {
        Arquivo arquivo = this.arquivoService.findByIdwithValidation(arquivoId);
        List<ExtratoMensalCartaoDTO> extratos = this.parseExtratos(arquivo.getConteudo());
        extratoIterator = extratos.iterator();
    }

    private List<ExtratoMensalCartaoDTO> parseExtratos(byte[] arquivoConteudo) throws Exception {
        ExtratoMensalCartaoCSVParser extratoMensalCartaoCSVParser = new ExtratoMensalCartaoCSVParser();
        return extratoMensalCartaoCSVParser.parse(new ByteArrayInputStream(arquivoConteudo));
    }

    private ExtratoMensalCartaoDTO getNextExtrato() {
        if (extratoIterator != null && extratoIterator.hasNext()) {
            return extratoIterator.next();
        } else {
            return null;
        }
    }
}
