package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.LancamentoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/lancamento")
public class LancamentoResource {
    @Autowired
    private final LancamentoService lancamentoService;

    public LancamentoResource(LancamentoService fluxoCaixaService) {
        this.lancamentoService = fluxoCaixaService;
    }

    @PostMapping
    public ResponseEntity<Object> createLancamento(@RequestBody LancamentoDTO lancamentoDTO) {
        Long id = this.lancamentoService.createLancamento(lancamentoDTO);
        return CustomSuccess.buildResponseEntity("Lançamento efetuado com sucesso.", "id", id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLancamento(@PathVariable Long id, @RequestBody LancamentoDTO lancamentoDTO) {
        this.lancamentoService.updateLancamento(id, lancamentoDTO);
        return CustomSuccess.buildResponseEntity("Lançamento editado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLancamento(@PathVariable Long id) {
        this.lancamentoService.deleteLancamento(id);
        return CustomSuccess.buildResponseEntity("Lançamento deletado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoDTO>> getLancamentos(Pageable pageable,
                                                              @RequestParam(required = false) String descricao,
                                                              @RequestParam(required = false) TipoLancamentoEnum tipo,
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) {
        return ResponseEntity.ok(lancamentoService.getLancamentos(pageable, descricao, tipo, dataInicio, dataFim));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        return ResponseEntity.ok(this.lancamentoService.getLancamentoDTO(id));
    }

    @PostMapping("/import-extrato-fatura-cartao")
    public ResponseEntity<Object> importExtratoFaturaCartao(@RequestParam MultipartFile file) throws Exception {
        this.lancamentoService.importExtratoFaturaCartao(file);
        return CustomSuccess.buildResponseEntity("Importação iniciada.");
    }
}
