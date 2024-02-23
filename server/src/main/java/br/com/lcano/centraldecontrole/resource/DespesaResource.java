package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.NovaDespesaDTO;
import br.com.lcano.centraldecontrole.dto.DespesaDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.service.DespesaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import br.com.lcano.centraldecontrole.util.MensagemConstantes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/despesa")
public class DespesaResource {
    @Autowired
    private final DespesaService despesaService;

    public DespesaResource(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<Object> gerarDespesa(@RequestBody NovaDespesaDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        despesaService.gerarDespesa(data, usuario);
        return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_ADICIONADA_COM_SUCESSO);
    }

    @PutMapping("/{idDespesa}")
    public ResponseEntity<Object> editarDespesa(@PathVariable Long idDespesa, @RequestBody NovaDespesaDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        despesaService.editarDespesa(idDespesa, data, usuario);
        return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_EDITADA_COM_SUCESSO);
    }

    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<Object> excluirDespesa(@PathVariable Long idDespesa) {
        despesaService.excluirDespesa(idDespesa);
        return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_EXCLUIDA_COM_SUCESSO);
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listarDespesasDoUsuario(
            HttpServletRequest request,
            @RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "mes", required = false) Integer mes
    ) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        List<DespesaDTO> despesasDTO = despesaService.listarDespesasDoUsuario(usuario.getId(), ano, mes);
        return ResponseEntity.ok(despesasDTO);
    }
}
