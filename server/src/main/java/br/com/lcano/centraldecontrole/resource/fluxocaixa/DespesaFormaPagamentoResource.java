package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaFormaPagamentoDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/despesa-forma-pagamento")
public class DespesaFormaPagamentoResource {
    @Autowired
    private final DespesaFormaPagamentoService despesaFormaPagamentoService;

    public DespesaFormaPagamentoResource(DespesaFormaPagamentoService formaPagamentoService) {
        this.despesaFormaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<DespesaFormaPagamentoDTO>> getTodasFormaPagamento() {
        List<DespesaFormaPagamentoDTO> formaPagamentoDTOS = despesaFormaPagamentoService.getTodasFormaPagamento();
        return ResponseEntity.ok(formaPagamentoDTOS);
    }
}
