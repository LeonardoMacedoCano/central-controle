package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ExtratoContaRegraRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExtratoContaRegraService {
    @Autowired
    private final ExtratoContaRegraRepository extratoContaRegraRepository;

    public List<ExtratoContaRegra> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario) {
        return extratoContaRegraRepository.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario, true);
    }
}
