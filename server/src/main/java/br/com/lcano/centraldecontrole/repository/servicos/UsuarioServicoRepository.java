package br.com.lcano.centraldecontrole.repository.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.UsuarioServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioServicoRepository extends JpaRepository<UsuarioServico, Long> {
    UsuarioServico findByUsuarioIdAndServicoId(Long id, Long idServico);
}
