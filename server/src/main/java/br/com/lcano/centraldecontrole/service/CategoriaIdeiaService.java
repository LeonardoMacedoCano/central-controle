package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.repository.CategoriaIdeiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaIdeiaService {
    @Autowired
    private final CategoriaIdeiaRepository categoriaIdeiaRepository;

    public CategoriaIdeiaService(CategoriaIdeiaRepository categoriaIdeiaRepository) {
        this.categoriaIdeiaRepository = categoriaIdeiaRepository;
    }

    public List<CategoriaDTO> getTodasCategoriasIdeia() {
        return categoriaIdeiaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }
}
