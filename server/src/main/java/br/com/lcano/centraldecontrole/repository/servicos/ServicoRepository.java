package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long>, JpaSpecificationExecutor<Servico> {
}
