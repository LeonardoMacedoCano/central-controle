package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.CategoriaDespesaResponseDTO;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaDespesaService {
    @Autowired
    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public CategoriaDespesaService(CategoriaDespesaRepository categoriaDespesaRepository) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    public List<CategoriaDespesaResponseDTO> getTodasCategoriasDespesa() {
        return categoriaDespesaRepository.findAll().stream().map(CategoriaDespesaResponseDTO::new).toList();
    }
}
