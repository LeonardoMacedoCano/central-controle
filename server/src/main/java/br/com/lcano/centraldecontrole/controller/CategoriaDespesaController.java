package br.com.lcano.centraldecontrole.controller;

import br.com.lcano.centraldecontrole.dto.CategoriaDespesaResponseDTO;
import br.com.lcano.centraldecontrole.service.CategoriaDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categoriadespesa")
public class CategoriaDespesaController {
    @Autowired
    private CategoriaDespesaService categoriaDespesaService;

    @GetMapping("/getTodasCategoriasDespesa")
    public ResponseEntity<List<CategoriaDespesaResponseDTO>> getTodasCategoriasDespesa() {
        List<CategoriaDespesaResponseDTO> categoriasDespesa = categoriaDespesaService.getTodasCategoriasDespesa();
        return ResponseEntity.ok(categoriasDespesa);
    }
}
