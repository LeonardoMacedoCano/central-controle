package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class NotificacaoSpecifications {

    public static Specification<Notificacao> hasDataHoraBetween(Date start, Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dataHora"), start, end);
    }

    public static Specification<Notificacao> hasDataHoraNotEqual(Date startOfDay, Date endOfDay) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.lessThan(root.get("dataHora"), startOfDay),
                        criteriaBuilder.greaterThan(root.get("dataHora"), endOfDay)
                );
    }

    public static Specification<Notificacao> hasDataHoraAfter(Date startOfDay) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dataHora"), startOfDay);
    }

    public static Specification<Notificacao> hasDataHoraBefore(Date endOfDay) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("dataHora"), endOfDay);
    }

    public static Specification<Notificacao> hasDataHoraGreaterOrEqual(Date startOfDay) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dataHora"), startOfDay);
    }

    public static Specification<Notificacao> hasDataHoraLessOrEqual(Date endOfDay) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dataHora"), endOfDay);
    }

    public static Specification<Notificacao> hasMensagem(String mensagem) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("mensagem"), mensagem);
    }

    public static Specification<Notificacao> hasMensagemNot(String mensagem) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("mensagem"), mensagem);
    }

    public static Specification<Notificacao> hasMensagemLike(String mensagem) {
        return (root, query, builder) -> builder.like(root.get("mensagem"), "%" + mensagem + "%");
    }

    public static Specification<Notificacao> hasLink(String link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("link"), link);
    }

    public static Specification<Notificacao> hasLinkNot(String link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("link"), link);
    }

    public static Specification<Notificacao> hasLinkLike(String link) {
        return (root, query, builder) -> builder.like(root.get("link"), "%" + link + "%");
    }

    public static Specification<Notificacao> hasVisto(Boolean visto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visto"), visto);
    }

    public static Specification<Notificacao> hasVistoNot(Boolean visto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("visto"), visto);
    }
}
