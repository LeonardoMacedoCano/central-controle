package br.com.lcano.centraldecontrole.repository.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoCaixaConfigRepository extends JpaRepository<FluxoCaixaConfig, Long> {
    FluxoCaixaConfig findByUsuario(Usuario usuario);
}
