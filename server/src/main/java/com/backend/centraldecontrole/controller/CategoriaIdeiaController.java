package com.backend.centraldecontrole.controller;

import com.backend.centraldecontrole.dto.CategoriaIdeiaResponseDTO;
import com.backend.centraldecontrole.service.CategoriaIdeiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("categoriaideia")
public class CategoriaIdeiaController {
    @Autowired
    private CategoriaIdeiaService categoriaIdeiaService;

    @GetMapping("/getTodasCategoriasIdeia")
    public ResponseEntity<List<CategoriaIdeiaResponseDTO>> getTodasCategoriasIdeia() {
        List<CategoriaIdeiaResponseDTO> categoriasIdeia = categoriaIdeiaService.getTodasCategoriasIdeia();
        return ResponseEntity.ok(categoriasIdeia);
    }
}
