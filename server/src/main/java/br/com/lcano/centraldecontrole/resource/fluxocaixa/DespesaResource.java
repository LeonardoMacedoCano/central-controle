package br.com.lcano.centraldecontrole.resource.fluxocaixa;

import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaDTO;
import br.com.lcano.centraldecontrole.service.fluxocaixa.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
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

     */

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> getDespesaByIdWithParcelas(@PathVariable Long id) {
        return ResponseEntity.ok(DespesaDTO.converterParaDTO(this.despesaService.getDespesaByIdWithParcelas(id)));
    }
}
