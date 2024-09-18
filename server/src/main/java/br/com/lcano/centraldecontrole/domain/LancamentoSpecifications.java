package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class LancamentoSpecifications {
    public static Specification<Lancamento> hasUsuarioId(Long usuarioId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId);
    }

    public static Specification<Lancamento> hasTipos(List<TipoLancamentoEnum> tipos) {
        return (root, query, criteriaBuilder) ->
                (tipos == null || tipos.isEmpty()) ? null : root.get("tipo").in(tipos);
    }

    public static Specification<Lancamento> hasDescricaoLike(String descricao) {
        return (root, query, criteriaBuilder) ->
                (descricao == null || descricao.isEmpty()) ? null :
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("descricao")),
                                "%" + descricao.toLowerCase() + "%"
                        );
    }

    public static Specification<Lancamento> hasDataLancamentoAfter(Date dataInicio) {
        return (root, query, criteriaBuilder) ->
                (dataInicio == null) ? null :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataLancamento"), dataInicio);
    }

    public static Specification<Lancamento> hasDataLancamentoBefore(Date dataFim) {
        return (root, query, criteriaBuilder) ->
                (dataFim == null) ? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dataLancamento"), dataFim);
    }
}
