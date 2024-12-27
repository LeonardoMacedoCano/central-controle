package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaConfig;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.BaseDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaConfigDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.FluxoCaixaConfigException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.FluxoCaixaConfigRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FluxoCaixaConfigService extends AbstractGenericService<FluxoCaixaConfig, Long> {
    @Autowired
    private final FluxoCaixaConfigRepository repository;
    @Autowired
    private final UsuarioUtil usuarioUtil;

    @Override
    protected JpaRepository<FluxoCaixaConfig, Long> getRepository() {
        return repository;
    }

    @Override
    protected FluxoCaixaConfigDTO getDtoInstance() {
        return new FluxoCaixaConfigDTO();
    }

    public FluxoCaixaConfig findByUsuario() {
        return repository.findByUsuario(
                usuarioUtil.getUsuarioAutenticado()
        );
    }

    @Override
    @Transactional
    public <D extends BaseDTO<FluxoCaixaConfig>> D saveAsDto(D dto) {
        FluxoCaixaConfig fluxoCaixaConfig = dto.toEntity();
        fluxoCaixaConfig.setUsuario(usuarioUtil.getUsuarioAutenticado());

        fluxoCaixaConfig = repository.save(fluxoCaixaConfig);

        return (D) new FluxoCaixaConfigDTO().fromEntity(fluxoCaixaConfig);
    }

    public DespesaCategoria getDespesaCategoriaPadrao() {
        return findByUsuario().getDespesaCategoriaPadrao();
    }

    public ReceitaCategoria getReceitaCategoriaPadrao() {
        return findByUsuario().getReceitaCategoriaPadrao();
    }

    public void validateConfig() {
        FluxoCaixaConfig fluxoCaixaConfig = findByUsuario();

        if (fluxoCaixaConfig == null) throw new FluxoCaixaConfigException.ConfigNaoEncontrada();
        if (fluxoCaixaConfig.getDespesaCategoriaPadrao() == null) throw new FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada(TipoLancamentoEnum.DESPESA.getDescricao());
        if (fluxoCaixaConfig.getReceitaCategoriaPadrao() == null) throw new FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada(TipoLancamentoEnum.RECEITA.getDescricao());
    }

}
