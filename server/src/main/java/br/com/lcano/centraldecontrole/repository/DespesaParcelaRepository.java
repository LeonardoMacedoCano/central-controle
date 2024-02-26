package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DespesaParcelaRepository extends JpaRepository<DespesaParcela, Long> {
    List<DespesaParcela> findByDespesaAndDataVencimentoBetween(Despesa despesa, Date dataInicio, Date dataFim);
}
