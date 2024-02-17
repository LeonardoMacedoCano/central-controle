package br.com.lcano.centraldecontrole.controller;

import br.com.lcano.centraldecontrole.dto.CategoriaTarefaResponseDTO;
import br.com.lcano.centraldecontrole.service.CategoriaTarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categoriatarefa")
public class CategoriaTarefaController {
    @Autowired
    private CategoriaTarefaService categoriaTarefaService;

    @GetMapping("/getTodasCategoriasTarefa")
    public ResponseEntity<List<CategoriaTarefaResponseDTO>> getTodasCategoriasTarefa() {
        List<CategoriaTarefaResponseDTO> categoriasTarefa = categoriaTarefaService.getTodasCategoriasTarefa();
        return ResponseEntity.ok(categoriasTarefa);
    }
}
