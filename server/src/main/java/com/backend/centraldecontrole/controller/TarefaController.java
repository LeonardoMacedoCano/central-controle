package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.dto.TarefaRequestDTO;
import com.backend.centraldecontrole.dto.TarefaResponseDTO;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.service.TarefaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("tarefa")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @PostMapping("/add")
    public ResponseEntity<String> adicionarTarefa(@RequestBody TarefaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return tarefaService.adicionarTarefa(data, usuario);
    }

    @PutMapping("/editar/{idTarefa}")
    public ResponseEntity<String> editarTarefa(@PathVariable Long idTarefa, @RequestBody TarefaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return tarefaService.editarTarefa(idTarefa, data, usuario);
    }

    @DeleteMapping("/excluir/{idTarefa}")
    public ResponseEntity<String> excluirTarefa(@PathVariable Long idTarefa) {
        return tarefaService.excluirTarefa(idTarefa);
    }

    @GetMapping("/listar")
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
