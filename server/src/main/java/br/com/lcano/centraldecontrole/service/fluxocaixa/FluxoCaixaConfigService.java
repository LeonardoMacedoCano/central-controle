package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaCategoriaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaConfigDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaCategoriaDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.FluxoCaixaConfigRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FluxoCaixaConfigService {
    @Autowired
    private final FluxoCaixaConfigRepository fluxoCaixaConfigRepository;
    @Autowired
    private final UsuarioUtil usuarioUtil;
    @Autowired
    private final DespesaCategoriaService despesaCategoriaService;
    @Autowired
    private final ReceitaCategoriaService receitaCategoriaService;

    public FluxoCaixaConfigDTO getConfig() {
        return new FluxoCaixaConfigDTO().fromEntity(
                fluxoCaixaConfigRepository.findByUsuario(
                        usuarioUtil.getUsuarioAutenticado()
                )
        );
    }

    @Transactional
    public Long saveConfig(FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        return fluxoCaixaConfigRepository.save(this.buildFluxoCaixaConfig(fluxoCaixaConfigDTO)).getId();
    }

    private FluxoCaixaConfig buildFluxoCaixaConfig(FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        FluxoCaixaConfig fluxoCaixaConfig = new FluxoCaixaConfig();
        fluxoCaixaConfig.setId(fluxoCaixaConfigDTO.getId());
        fluxoCaixaConfig.setUsuario(usuarioUtil.getUsuarioAutenticado());
        fluxoCaixaConfig.setMetaLimiteDespesaMensal(fluxoCaixaConfigDTO.getMetaLimiteDespesaMensal());
        fluxoCaixaConfig.setMetaAporteMensal(fluxoCaixaConfigDTO.getMetaAporteMensal());
        fluxoCaixaConfig.setMetaAporteTotal(fluxoCaixaConfigDTO.getMetaAporteTotal());
        fluxoCaixaConfig.setDiaPadraoVencimentoFatura(fluxoCaixaConfigDTO.getDiaPadraoVencimentoFatura());


        if (fluxoCaixaConfigDTO.getDespesaCategoriaPadrao() != null
                && fluxoCaixaConfigDTO.getDespesaCategoriaPadrao().getId() != null) {
            fluxoCaixaConfig.setDespesaCategoriaPadrao(
                    despesaCategoriaService.findById(
                            fluxoCaixaConfigDTO.getDespesaCategoriaPadrao().getId()
                    )
            );
        } else {
            fluxoCaixaConfig.setDespesaCategoriaPadrao(null);
        }

        if (fluxoCaixaConfigDTO.getReceitaCategoriaPadrao() != null
                && fluxoCaixaConfigDTO.getReceitaCategoriaPadrao().getId() != null) {
            fluxoCaixaConfig.setReceitaCategoriaPadrao(
                    receitaCategoriaService.findById(
                            fluxoCaixaConfigDTO.getReceitaCategoriaPadrao().getId()
                    )
            );
        } else {
            fluxoCaixaConfig.setReceitaCategoriaPadrao(null);
        }

        if (fluxoCaixaConfigDTO.getReceitaCategoriaParaGanhoAtivo() != null
                && fluxoCaixaConfigDTO.getReceitaCategoriaParaGanhoAtivo().getId() != null) {
            fluxoCaixaConfig.setReceitaCategoriaParaGanhoAtivo(
                    receitaCategoriaService.findById(
                            fluxoCaixaConfigDTO.getReceitaCategoriaParaGanhoAtivo().getId()
                    )
            );
        } else {
            fluxoCaixaConfig.setReceitaCategoriaPadrao(null);
        }

        return fluxoCaixaConfig;
    }

    public DespesaCategoriaDTO getDespesaCategoriaPadrao() {
        return getConfig().getDespesaCategoriaPadrao();
    }

    public ReceitaCategoriaDTO getReceitaCategoriaPadrao() {
        return getConfig().getReceitaCategoriaPadrao();
    }
}
