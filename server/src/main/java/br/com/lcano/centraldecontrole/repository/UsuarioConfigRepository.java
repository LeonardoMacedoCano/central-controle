package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioConfigRepository extends JpaRepository<UsuarioConfig, Long> {
    Optional<UsuarioConfig> findByUsuario(Usuario usuario);
}
