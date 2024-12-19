package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaConfigDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.FluxoCaixaConfigRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
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
        return FluxoCaixaConfigDTO.converterParaDTO(fluxoCaixaConfigRepository.findByUsuario(usuarioUtil.getUsuarioAutenticado()));
    }

    public void saveConfig(FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        fluxoCaixaConfigRepository.save(this.buildFluxoCaixaConfig(fluxoCaixaConfigDTO));
    }

    private FluxoCaixaConfig buildFluxoCaixaConfig(FluxoCaixaConfigDTO fluxoCaixaConfigDTO) {
        FluxoCaixaConfig fluxoCaixaConfig = new FluxoCaixaConfig();
        fluxoCaixaConfig.setId(fluxoCaixaConfigDTO.getId());
        fluxoCaixaConfig.setUsuario(usuarioUtil.getUsuarioAutenticado());
        fluxoCaixaConfig.setValorMetaSaldoMensal(fluxoCaixaConfigDTO.getValorMetaSaldoMensal());
        fluxoCaixaConfig.setDiaPadraoVencimentoFatura(fluxoCaixaConfigDTO.getDiaPadraoVencimentoFatura());

        if (fluxoCaixaConfigDTO.getDespesaCategoriaPadrao() != null
                && fluxoCaixaConfigDTO.getDespesaCategoriaPadrao().getId() != null) {
            fluxoCaixaConfig.setDespesaCategoriaPadrao(
                    despesaCategoriaService.getCategoriaById(
                            fluxoCaixaConfigDTO.getDespesaCategoriaPadrao().getId()
                    )
            );
        } else {
            fluxoCaixaConfig.setDespesaCategoriaPadrao(null);
        }

        if (fluxoCaixaConfigDTO.getReceitaCategoriaPadrao() != null
                && fluxoCaixaConfigDTO.getReceitaCategoriaPadrao().getId() != null) {
            fluxoCaixaConfig.setReceitaCategoriaPadrao(
                    receitaCategoriaService.getCategoriaById(
                            fluxoCaixaConfigDTO.getReceitaCategoriaPadrao().getId()
                    )
            );
        } else {
            fluxoCaixaConfig.setReceitaCategoriaPadrao(null);
        }

        return fluxoCaixaConfig;
    }
}
