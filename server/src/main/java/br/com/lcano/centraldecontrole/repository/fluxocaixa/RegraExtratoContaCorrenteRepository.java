package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RegraExtratoContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegraExtratoContaCorrenteRepository extends JpaRepository<RegraExtratoContaCorrente, Long> {
    List<RegraExtratoContaCorrente> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario, Boolean ativo);
}
