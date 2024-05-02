package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
