package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.dto.TarefaResponseDTO;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.service.TarefaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("tarefa")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

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
