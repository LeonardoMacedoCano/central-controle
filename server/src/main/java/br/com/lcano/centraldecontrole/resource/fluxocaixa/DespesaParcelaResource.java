package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/despesa-parcela")
public class DespesaParcelaResource {
    @Autowired
    private final DespesaParcelaService despesaParcelaService;

    public DespesaParcelaResource(DespesaParcelaService despesaParcelaService) {
        this.despesaParcelaService = despesaParcelaService;
    }

    @GetMapping("/valor-total-mensal")
    public ResponseEntity<Double> getValorTotalParcelasMensal(@RequestParam("mes") int mes, @RequestParam("ano") int ano) {
        double valorTotal = despesaParcelaService.calcularValorTotalParcelasMensal(ano, mes);
        return ResponseEntity.ok(valorTotal);
    }
}
