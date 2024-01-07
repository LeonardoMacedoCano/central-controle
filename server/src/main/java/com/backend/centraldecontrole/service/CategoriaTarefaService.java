package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.CategoriaTarefaResponseDTO;
import com.backend.centraldecontrole.repository.CategoriaTarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTarefaService {
    @Autowired
    private CategoriaTarefaRepository categoriaTarefaRepository;

    public List<CategoriaTarefaResponseDTO> getTodasCategoriasTarefa() {
        return categoriaTarefaRepository.findAll().stream().map(CategoriaTarefaResponseDTO::new).toList();
    }
}
