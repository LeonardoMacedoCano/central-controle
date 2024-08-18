package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.CategoriaDespesa;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.DespesaException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.CategoriaDespesaRepository;
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
        return this.categoriaDespesaRepository.findAll().stream().map(CategoriaDTO::converterParaDTO).toList();
    }

    public CategoriaDespesa getCategoriaDespesaById(Long id) {
        return this.categoriaDespesaRepository.findById(id)
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(id));
    }
}
