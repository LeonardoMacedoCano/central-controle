package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Notificacao;
import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.NotificacaoDTO;
import br.com.lcano.centraldecontrole.enums.OperatorFilter;
import br.com.lcano.centraldecontrole.repository.NotificacaoRepository;
import br.com.lcano.centraldecontrole.repository.NotificacaoSpecifications;
import br.com.lcano.centraldecontrole.util.DateUtil;
import br.com.lcano.centraldecontrole.util.FilterUtil;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class NotificacaoService extends AbstractGenericService<Notificacao, Long> {
    private final NotificacaoRepository repository;
    private DateUtil dateUtil;
    private UsuarioUtil usuarioUtil;

    @Override
    protected NotificacaoRepository getRepository() {
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

    public Long getTotalNotificacoesNaoLidas() {
        return repository.countByUsuarioIdAndVistoFalse(usuarioUtil.getUsuarioAutenticado().getId());
    }

    public Page<NotificacaoDTO> search(Pageable pageable, List<FilterDTO> filterDTOs) {
        Specification<Notificacao> combinedSpecification = FilterUtil.buildSpecificationsFromDTO(filterDTOs, this::applyFieldSpecification);
        return repository.findAll(combinedSpecification, pageable)
                .map(entity -> getDtoInstance().fromEntity(entity));
    }

    public void alterStatus(Long id, Boolean visto) {
        Notificacao notificacao = findById(id);
        notificacao.setVisto(!visto);
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
        OperatorFilter filterEnum = OperatorFilter.fromSymbol(operator);
        Date date = DateUtil.parseDate(value);

        LocalDateTime startOfDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        Date startOfDayDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endOfDayDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasDataHoraBetween(startOfDayDate, endOfDayDate);
            case DIFERENTE -> NotificacaoSpecifications.hasDataHoraNotEqual(startOfDayDate, endOfDayDate);
            case MAIOR -> NotificacaoSpecifications.hasDataHoraAfter(startOfDayDate);
            case MENOR -> NotificacaoSpecifications.hasDataHoraBefore(endOfDayDate);
            case MAIOR_OU_IGUAL -> NotificacaoSpecifications.hasDataHoraGreaterOrEqual(startOfDayDate);
            case MENOR_OU_IGUAL -> NotificacaoSpecifications.hasDataHoraLessOrEqual(endOfDayDate);
            default -> null;
        };
    }


    private Specification<Notificacao> applyMensagemSpecification(String operator, String value) {
        OperatorFilter filterEnum = OperatorFilter.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasMensagem(value);
            case DIFERENTE -> NotificacaoSpecifications.hasMensagemNot(value);
            case CONTEM -> NotificacaoSpecifications.hasMensagemLike(value);
            default -> null;
        };
    }

    private Specification<Notificacao> applyLinkSpecification(String operator, String value) {
        OperatorFilter filterEnum = OperatorFilter.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasLink(value);
            case DIFERENTE -> NotificacaoSpecifications.hasLinkNot(value);
            case CONTEM -> NotificacaoSpecifications.hasLinkLike(value);
            default -> null;
        };
    }

    private Specification<Notificacao> applyVistoSpecification(String operator, String value) {
        OperatorFilter filterEnum = OperatorFilter.fromSymbol(operator);
        Boolean visto = Objects.equals(value, "true");

        return switch (filterEnum) {
            case IGUAL -> NotificacaoSpecifications.hasVisto(visto);
            case DIFERENTE -> NotificacaoSpecifications.hasVistoNot(visto);
            default -> null;
        };
    }
}
