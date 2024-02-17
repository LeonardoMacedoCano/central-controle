package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.model.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {
}
