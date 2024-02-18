package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaTarefaResponseDTO;
import br.com.lcano.centraldecontrole.repository.CategoriaTarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaTarefaService {
    @Autowired
    private final CategoriaTarefaRepository categoriaTarefaRepository;

    public CategoriaTarefaService(CategoriaTarefaRepository categoriaTarefaRepository) {
        this.categoriaTarefaRepository = categoriaTarefaRepository;
    }

    public List<CategoriaTarefaResponseDTO> getTodasCategoriasTarefa() {
        return categoriaTarefaRepository.findAll().stream().map(CategoriaTarefaResponseDTO::new).toList();
    }
}
