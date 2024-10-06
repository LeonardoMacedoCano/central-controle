package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.LancamentoDTO;
import br.com.lcano.centraldecontrole.service.LancamentoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/lancamento")
public class LancamentoResource {
    @Autowired
    private final LancamentoService lancamentoService;

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

    @PostMapping("/search")
    public ResponseEntity<Page<LancamentoDTO>> getLancamentos(Pageable pageable,
                                                              @RequestBody(required = false) List<FilterDTO> filterDTOs) {
        return ResponseEntity.ok(lancamentoService.getLancamentos(pageable, filterDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        return ResponseEntity.ok(this.lancamentoService.getLancamentoDTO(id));
    }

    @PostMapping("/import-extrato-fatura-cartao")
    public ResponseEntity<Object> importExtratoFaturaCartao(@RequestParam MultipartFile file,
                                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataVencimento) throws Exception {
        this.lancamentoService.importExtratoFaturaCartao(file, dataVencimento);
        return CustomSuccess.buildResponseEntity("Importação iniciada.");
    }
}
