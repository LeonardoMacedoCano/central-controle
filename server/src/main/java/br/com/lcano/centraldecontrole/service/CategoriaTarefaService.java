package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
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

    public List<CategoriaDTO> getTodasCategoriasTarefa() {
        return categoriaTarefaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }
}
