package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.service.fluxocaixa.ExtratoFluxoCaixaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/api/extrato-fluxo-caixa")
public class ExtratoFluxoCaixaResource {

    @Autowired
    private final ExtratoFluxoCaixaService service;

    @PostMapping("/import-extrato-mensal-cartao")
    public ResponseEntity<Object> importExtratoCartaoCartao(@RequestParam MultipartFile file,
                                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataVencimento) throws Exception {
        this.service.importExtratoCartaoCartao(file, dataVencimento);
        return CustomSuccess.buildResponseEntity("Importação iniciada.");
    }

    @PostMapping("/import-extrato-conta-corrente")
    public ResponseEntity<Object> importExtratoContaCorrente(@RequestParam MultipartFile file) throws Exception {
        this.service.importExtratoContaCorrente(file);
        return CustomSuccess.buildResponseEntity("Importação iniciada.");
    }

    @PostMapping("/import-extrato-ativos-b3")
    public ResponseEntity<Object> importExtratoAtivosB3(@RequestParam MultipartFile file) throws Exception {
        this.service.importExtratoAtivosB3(file);
        return CustomSuccess.buildResponseEntity("Importação iniciada.");
    }
}
