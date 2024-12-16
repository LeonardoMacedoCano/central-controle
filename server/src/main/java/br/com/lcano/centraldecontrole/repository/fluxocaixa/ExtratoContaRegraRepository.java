package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtratoContaRegraRepository extends JpaRepository<ExtratoContaRegra, Long> {
    List<ExtratoContaRegra> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario, Boolean ativo);
}
