package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, JpaSpecificationExecutor<Lancamento> {

    static Specification<Lancamento> withUsuarioAndTipoAndDateRange(Usuario usuario, TipoLancamento tipo, Date inicio, Date fim) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("usuario"), usuario),
                    criteriaBuilder.equal(root.get("tipo"), tipo)
            );

            switch (tipo) {
                case DESPESA -> {
                    Join<Object, Object> despesaJoin = root.join("despesa", JoinType.INNER);
                    return criteriaBuilder.and(
                            predicates,
                            criteriaBuilder.between(despesaJoin.get("dataVencimento"), inicio, fim)
                    );
                }
                case RENDA -> {
                    Join<Object, Object> rendaJoin = root.join("renda", JoinType.INNER);
                    return criteriaBuilder.and(
                            predicates,
                            criteriaBuilder.between(rendaJoin.get("dataRecebimento"), inicio, fim)
                    );
                }
                case ATIVO -> {
                    Join<Object, Object> ativoJoin = root.join("ativo", JoinType.INNER);
                    return criteriaBuilder.and(
                            predicates,
                            criteriaBuilder.between(ativoJoin.get("dataMovimento"), inicio, fim)
                    );
                }
                default -> {
                    return predicates;
                }
            }
        };
    }
}
