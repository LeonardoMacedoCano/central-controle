package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoCategoriaRepository extends JpaRepository<ServicoCategoria, Long> {
}
