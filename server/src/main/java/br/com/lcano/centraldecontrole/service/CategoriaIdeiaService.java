package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaIdeiaResponseDTO;
import br.com.lcano.centraldecontrole.repository.CategoriaIdeiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaIdeiaService {
    @Autowired
    private CategoriaIdeiaRepository categoriaIdeiaRepository;

    public List<CategoriaIdeiaResponseDTO> getTodasCategoriasIdeia() {
        return categoriaIdeiaRepository.findAll().stream().map(CategoriaIdeiaResponseDTO::new).toList();
    }
}
