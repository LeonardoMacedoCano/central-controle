package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.FormaPagamentoDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/formapagamento")
public class FormaPagamentoResource {
    @Autowired
    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoResource(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> getTodasFormaPagamento() {
        List<FormaPagamentoDTO> formaPagamentoDTOS = formaPagamentoService.getTodasFormaPagamento();
        return ResponseEntity.ok(formaPagamentoDTOS);
    }
}
