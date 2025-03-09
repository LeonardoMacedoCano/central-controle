package br.com.lcano.centraldecontrole.repository;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class NotificacaoSpecifications {

    public static Specification<Notificacao> hasDataHoraAfter(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dataHora"), dataHora);
    }

    public static Specification<Notificacao> hasDataHoraBefore(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("dataHora"), dataHora);
    }

    public static Specification<Notificacao> hasDataHoraEqual(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dataHora"), dataHora);
    }

    public static Specification<Notificacao> hasDataHoraNotEqual(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("dataHora"), dataHora);
    }

    public static Specification<Notificacao> hasDataHoraGreaterOrEqual(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dataHora"), dataHora);
    }

    public static Specification<Notificacao> hasDataHoraLessOrEqual(LocalDateTime dataHora) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dataHora"), dataHora);
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

    public static Specification<Notificacao> hasVisto(String visto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visto"), visto);
    }

    public static Specification<Notificacao> hasVistoNot(String visto) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("visto"), visto);
    }
}
