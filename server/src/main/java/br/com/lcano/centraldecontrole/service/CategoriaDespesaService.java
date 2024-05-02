package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.DespesaException;
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

    public List<CategoriaDTO> getTodasCategoriasDespesa() {
        return categoriaDespesaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }

    public CategoriaDespesa getCategoriaDespesaById(Long id) {
        return categoriaDespesaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(id));
    }
}
