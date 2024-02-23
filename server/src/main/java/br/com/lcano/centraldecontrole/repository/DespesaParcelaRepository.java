package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaParcelaRepository extends JpaRepository<DespesaParcela, Long> {
}
