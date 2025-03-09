package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>, JpaSpecificationExecutor<Notificacao> {
}
