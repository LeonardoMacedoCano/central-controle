package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.CategoriaDespesaResponseDTO;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaDespesaService {
    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    public List<CategoriaDespesaResponseDTO> getTodasCategoriasDespesa() {
        return categoriaDespesaRepository.findAll().stream().map(CategoriaDespesaResponseDTO::new).toList();
    }
}
