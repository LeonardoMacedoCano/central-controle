package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoCaixaParametroRepository extends JpaRepository<FluxoCaixaParametro, Long> {
    FluxoCaixaParametro findByUsuario(Usuario usuario);
}
