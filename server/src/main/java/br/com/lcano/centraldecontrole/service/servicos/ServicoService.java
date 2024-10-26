package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.servicos.Servico;
import br.com.lcano.centraldecontrole.dto.FilterDTO;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoCategoriaDTO;
import br.com.lcano.centraldecontrole.dto.servicos.ServicoDTO;
import br.com.lcano.centraldecontrole.enums.OperatorFilterEnum;
import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoRepository;
import br.com.lcano.centraldecontrole.repository.servicos.ServicoSpecifications;
import br.com.lcano.centraldecontrole.util.FilterUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServicoService {
    @Autowired
    private final ServicoRepository servicoRepository;
    @Autowired
    private final DockerService dockerService;
    @Autowired
    private final UsuarioServicoService usuarioServicoService;
    @Autowired
    private final ServicoCategoriaService servicoCategoriaService;

    public Page<ServicoDTO> getServicos(Pageable pageable, List<FilterDTO> filterDTOs) {
        Specification<Servico> combinedSpecification = FilterUtil.buildSpecificationsFromDTO(filterDTOs, this::applyFieldSpecification);
        return servicoRepository.findAll(combinedSpecification, pageable)
                .map(servico -> {
                    DockerStatusEnum status = dockerService.getContainerStatusByName(servico.getNome());
                    Boolean hasPermission = usuarioServicoService.hasPermissionForService(servico.getId());
                    List<ServicoCategoriaDTO> categorias = servicoCategoriaService.findByServicoId(servico.getId());
                    return ServicoDTO.converterParaDTO(servico, status, hasPermission, categorias);
                });
    }

    public void changeContainerStatusByName(String name, ContainerActionEnum action) {
        this.dockerService.changeContainerStatusByName(name, action);
    }

    public List<ServicoCategoriaDTO> getAllServicoCategoria() {
        return servicoCategoriaService.getAll();
    }

    private Specification<Servico> applyFieldSpecification(FilterDTO filterDTO) {
        String field = filterDTO.getField();
        String operator = filterDTO.getOperator();
        String value = filterDTO.getValue();

        return switch (field) {
            case "nome" -> applyNomeSpecification(operator, value);
            case "descricao" -> applyDescricaoSpecification(operator, value);
            case "porta" -> applyPortaSpecification(operator, value);
            case "categorias" -> applyCategoriasSpecification(operator, value);
            default -> null;
        };
    }

    private Specification<Servico> applyNomeSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> ServicoSpecifications.hasNome(value);
            case DIFERENTE -> ServicoSpecifications.hasNomeNot(value);
            case CONTEM -> ServicoSpecifications.hasNomeLike(value);
            default -> null;
        };
    }

    private Specification<Servico> applyDescricaoSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> ServicoSpecifications.hasDescricao(value);
            case DIFERENTE -> ServicoSpecifications.hasDescricaoNot(value);
            case CONTEM -> ServicoSpecifications.hasDescricaoLike(value);
            default -> null;
        };
    }

    private Specification<Servico> applyPortaSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);
        Integer porta = Integer.valueOf(value);

        return switch (filterEnum) {
            case IGUAL -> ServicoSpecifications.hasPorta(porta);
            case DIFERENTE -> ServicoSpecifications.hasPortaNot(porta);
            case MAIOR -> ServicoSpecifications.hasPortaGreaterThan(porta);
            case MENOR -> ServicoSpecifications.hasPortaLessThan(porta);
            case MAIOR_OU_IGUAL -> ServicoSpecifications.hasPortaGreaterOrEqual(porta);
            case MENOR_OU_IGUAL -> ServicoSpecifications.hasPortaLessOrEqual(porta);
            default -> null;
        };
    }

    private Specification<Servico> applyCategoriasSpecification(String operator, String value) {
        OperatorFilterEnum filterEnum = OperatorFilterEnum.fromSymbol(operator);

        return switch (filterEnum) {
            case IGUAL -> ServicoSpecifications.hasCategorias(value);
            case DIFERENTE -> ServicoSpecifications.hasCategoriasNot(value);
            default -> null;
        };
    }
}
