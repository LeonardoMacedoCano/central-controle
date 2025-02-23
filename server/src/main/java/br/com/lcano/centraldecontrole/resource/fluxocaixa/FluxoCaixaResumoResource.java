package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.ResumoFluxoCaixaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FluxoCaixaResumoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/fluxo-caixa-resumo")
public class FluxoCaixaResumoResource {
    @Autowired
    private final FluxoCaixaResumoService service;

    @GetMapping
    public ResponseEntity<ResumoFluxoCaixaDTO> getResumoFluxoCaixa() {
        return ResponseEntity.ok(service.getResumoFluxoCaixa());
    }
}
