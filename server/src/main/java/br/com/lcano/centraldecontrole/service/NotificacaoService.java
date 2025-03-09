package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.NotificacaoDTO;
import br.com.lcano.centraldecontrole.enums.OperatorFilterEnum;
import br.com.lcano.centraldecontrole.repository.NotificacaoRepository;
import br.com.lcano.centraldecontrole.repository.NotificacaoSpecifications;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.FilterUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class NotificacaoService extends AbstractGenericService<Notificacao, Long> {
    private final NotificacaoRepository repository;
    private DateUtil dateUtil;
    private UsuarioUtil usuarioUtil;

    @Override
    protected JpaRepository<Notificacao, Long> getRepository() {
        return repository;
    }

    @Override
    protected NotificacaoDTO getDtoInstance() {
        return new NotificacaoDTO();
    }

    public void createAndSaveNotificacao(String mensagem, String link) {
        Notificacao notificacao = new Notificacao();
        notificacao.setDataHora(dateUtil.getDataHoraAtual());
        notificacao.setUsuario(usuarioUtil.getUsuarioAutenticado());
        notificacao.setVisto(Boolean.FALSE);
        notificacao.setMensagem(mensagem);
        notificacao.setLink(link);

        save(notificacao);
    }

    public Page<NotificacaoDTO> search(Pageable pageable, List<FilterDTO> filterDTOs) {
        Specification<Notificacao> combinedSpecification = FilterUtil.buildSpecificationsFromDTO(filterDTOs, this::applyFieldSpecification);
        return repository.findAll(combinedSpecification, pageable)
                .map(entity -> getDtoInstance().fromEntity(entity));
    }

    public void markAsRead(Long id) {
        Notificacao notificacao = findById(id);
        notificacao.setVisto(Boolean.TRUE);
        save(notificacao);
    }

    private Specification<Notificacao> applyFieldSpecification(FilterDTO filterDTO) {
        String field = filterDTO.getField();
        String operator = filterDTO.getOperator();
        String value = filterDTO.getValue();

        return switch (field) {
            case "dataHora" -> applyDataHoraSpecification(operator, value);
            case "mensagem" -> applyMensagemSpecification(operator, value);
            case "link" -> applyLinkSpecification(operator, value);
            case "visto" -> applyVistoSpecification(operator, value);
            default -> null;
        };
    }

    private Specification<Notificacao> applyDataHoraSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);
        LocalDateTime date = DateUtil.parseDateTime(value);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasDataHoraEqual(date);
            case DIFERENTE -> NotificacaoSpecifications.hasDataHoraNotEqual(date);
            case MAIOR -> NotificacaoSpecifications.hasDataHoraAfter(date);
            case MENOR -> NotificacaoSpecifications.hasDataHoraBefore(date);
            case MAIOR_OU_IGUAL -> NotificacaoSpecifications.hasDataHoraGreaterOrEqual(date);
            case MENOR_OU_IGUAL -> NotificacaoSpecifications.hasDataHoraLessOrEqual(date);
            default -> null;
        };
    }

    private Specification<Notificacao> applyMensagemSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasMensagem(value);
            case DIFERENTE -> NotificacaoSpecifications.hasMensagemNot(value);
            case CONTEM -> NotificacaoSpecifications.hasMensagemLike(value);
            default -> null;
        };
    }

    private Specification<Notificacao> applyLinkSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasLink(value);
            case DIFERENTE -> NotificacaoSpecifications.hasLinkNot(value);
            case CONTEM -> NotificacaoSpecifications.hasLinkLike(value);
            default -> null;
        };
    }

    private Specification<Notificacao> applyVistoSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasVisto(value);
            case DIFERENTE -> NotificacaoSpecifications.hasVistoNot(value);
            default -> null;
        };
    }
}
