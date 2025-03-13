package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Lancamento;
import br.com.lcano.centraldecontrole.enums.TipoLancamento;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class LancamentoSpecifications {
    public static Specification<Lancamento> hasTipos(List<TipoLancamento> tipos) {
        return (root, query, criteriaBuilder) -> root.get("tipo").in(tipos);
    }

    public static Specification<Lancamento> hasTiposNot(List<TipoLancamento> tipos) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.not(root.get("tipo").in(tipos));
    }

    public static Specification<Lancamento> hasDescricao(String descricao) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("descricao"), descricao);
    }

    public static Specification<Lancamento> hasDescricaoNot(String descricao) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("descricao"), descricao);
    }

    public static Specification<Lancamento> hasDescricaoLike(String descricao) {
        return (root, query, builder) -> builder.like(root.get("descricao"), "%" + descricao + "%");
    }

    public static Specification<Lancamento> hasDataLancamentoAfter(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dataLancamento"), data);
    }

    public static Specification<Lancamento> hasDataLancamentoBefore(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("dataLancamento"), data);
    }

    public static Specification<Lancamento> hasDataLancamentoEqual(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dataLancamento"), data);
    }

    public static Specification<Lancamento> hasDataLancamentoNotEqual(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("dataLancamento"), data);
    }

    public static Specification<Lancamento> hasDataLancamentoGreaterOrEqual(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dataLancamento"), data);
    }

    public static Specification<Lancamento> hasDataLancamentoLessOrEqual(Date data) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dataLancamento"), data);
    }
}
