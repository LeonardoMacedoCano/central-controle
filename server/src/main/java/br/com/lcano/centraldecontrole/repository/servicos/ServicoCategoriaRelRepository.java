package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.ServicoCategoriaRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoCategoriaRelRepository extends JpaRepository<ServicoCategoriaRel, Long> {
    List<ServicoCategoriaRel> findByServicoId(Long servicoId);
}
