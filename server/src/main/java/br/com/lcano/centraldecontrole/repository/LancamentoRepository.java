package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    @Query("SELECT l FROM Lancamento l WHERE l.usuario.id = :usuarioId " +
            "AND (:tipos IS NULL OR l.tipo IN :tipos) " +
            "AND (:descricao IS NULL OR LOWER(l.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) " +
            "AND (:dataInicio IS NULL OR l.dataLancamento >= :dataInicio) " +
            "AND (:dataFim IS NULL OR l.dataLancamento <= :dataFim)")
    Page<Lancamento> search(
            @Param("usuarioId") Long usuarioId,
            @Param("tipos") List<TipoLancamentoEnum> tipos,
            @Param("descricao") String descricao,
            @Param("dataInicio") Date dataInicio,
            @Param("dataFim") Date dataFim,
            Pageable pageable);
}
