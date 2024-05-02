package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.TarefaRequestDTO;
import br.com.lcano.centraldecontrole.dto.TarefaResponseDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.service.TarefaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaResource {
    @Autowired
    private final TarefaService tarefaService;

    public TarefaResource(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Object> gerarTarefa(@RequestBody TarefaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        tarefaService.gerarTarefa(data, usuario);
        return CustomSuccess.buildResponseEntity("Tarefa adicionada com sucesso.");
    }

    @PutMapping("/{idTarefa}")
    public ResponseEntity<Object> editarTarefa(@PathVariable Long idTarefa, @RequestBody TarefaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        tarefaService.editarTarefa(idTarefa, data, usuario);
        return CustomSuccess.buildResponseEntity("Tarefa editada com sucesso.");
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Object> excluirTarefa(@PathVariable Long idTarefa) {
        tarefaService.excluirTarefa(idTarefa);
        return CustomSuccess.buildResponseEntity("Tarefa excluída com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTarefasDoUsuario(
            HttpServletRequest request,
            @RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "mes", required = false) Integer mes
    ) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        List<TarefaResponseDTO> tarefasDTO = tarefaService.listarTarefasDoUsuario(usuario.getId(), ano, mes);
        return ResponseEntity.ok(tarefasDTO);
    }
}
