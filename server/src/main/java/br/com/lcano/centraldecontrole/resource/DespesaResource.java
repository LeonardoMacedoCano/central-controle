package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.DespesaResumoMensalDTO;
import br.com.lcano.centraldecontrole.service.DespesaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despesa")
public class DespesaResource {
    @Autowired
    private final DespesaService despesaService;

    public DespesaResource(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<Object> gerarDespesa(@RequestBody DespesaDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return CustomSuccess.buildResponseEntity("Despesa adicionada com sucesso.", "idDespesa", despesaService.gerarDespesa(data, usuario));
    }

    @PutMapping("/{idDespesa}")
    public ResponseEntity<Object> editarDespesa(@PathVariable Long idDespesa, @RequestBody DespesaDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        despesaService.editarDespesa(idDespesa, data, usuario);
        return CustomSuccess.buildResponseEntity("Despesa editada com sucesso.");
    }

    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<Object> excluirDespesa(@PathVariable Long idDespesa) {
        despesaService.excluirDespesa(idDespesa);
        return CustomSuccess.buildResponseEntity("Despesa excluída com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Page<DespesaResumoMensalDTO>> listarDespesaResumoMensalDTO(
            HttpServletRequest request,
            @RequestParam(name = "ano") Integer ano,
            @RequestParam(name = "mes") Integer mes,
            Pageable pageable
    ) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        Page<DespesaResumoMensalDTO> despesasDTO = despesaService.listarDespesaResumoMensalDTO(usuario.getId(), ano, mes, pageable);
        return ResponseEntity.ok(despesasDTO);
    }

    @GetMapping("/{idDespesa}")
    public ResponseEntity<DespesaDTO> getDespesaByIdWithParcelas(@PathVariable Long idDespesa) {
        return ResponseEntity.ok(DespesaDTO.converterParaDTO(this.despesaService.getDespesaByIdWithParcelas(idDespesa)));
    }
}
