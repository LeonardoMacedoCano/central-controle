package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.dto.DespesaRequestDTO;
import com.backend.centraldecontrole.dto.DespesaResponseDTO;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.service.DespesaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("despesa")
public class DespesaController {
    @Autowired
    private DespesaService despesaService;

    @PostMapping("/add")
    public ResponseEntity<String> adicionarDespesa(@RequestBody DespesaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return despesaService.adicionarDespesa(data, usuario);
    }

    @PutMapping("/editar/{idDespesa}")
    public ResponseEntity<String> editarDespesa(@PathVariable Long idDespesa, @RequestBody DespesaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return despesaService.editarDespesa(idDespesa, data, usuario);
    }

    @DeleteMapping("/excluir/{idDespesa}")
    public ResponseEntity<String> excluirDespesa(@PathVariable Long idDespesa) {
        return despesaService.excluirDespesa(idDespesa);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DespesaResponseDTO>> listarDespesasDoUsuario(
            HttpServletRequest request,
            @RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "mes", required = false) Integer mes
    ) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        List<DespesaResponseDTO> despesasDTO = despesaService.listarDespesasDoUsuario(usuario.getId(), ano, mes);
        return ResponseEntity.ok(despesasDTO);
    }
}
