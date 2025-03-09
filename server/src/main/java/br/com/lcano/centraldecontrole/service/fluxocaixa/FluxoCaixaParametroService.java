package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.FluxoCaixaParametro;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RendaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.FluxoCaixaParametroDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.FluxoCaixaConfigException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.FluxoCaixaParametroRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FluxoCaixaParametroService extends AbstractGenericService<FluxoCaixaParametro, Long> {
    @Autowired
    private final FluxoCaixaParametroRepository repository;
    @Autowired
    private final UsuarioUtil usuarioUtil;

    @Override
    protected JpaRepository<FluxoCaixaParametro, Long> getRepository() {
        return repository;
    }

    @Override
    protected FluxoCaixaParametroDTO getDtoInstance() {
        return new FluxoCaixaParametroDTO();
    }

    public FluxoCaixaParametro findByUsuario() {
        return repository.findByUsuario(
                usuarioUtil.getUsuarioAutenticado()
        );
    }

    public DespesaCategoria getDespesaCategoriaPadrao() {
        return findByUsuario().getDespesaCategoriaPadrao();
    }

    public RendaCategoria getRendaCategoriaPadrao() {
        return findByUsuario().getRendaCategoriaPadrao();
    }

    public void validateParametro() {
        FluxoCaixaParametro fluxoCaixaParametro = findByUsuario();

        if (fluxoCaixaParametro == null) throw new FluxoCaixaConfigException.ParametroNaoEncontrado();
        if (fluxoCaixaParametro.getDespesaCategoriaPadrao() == null) throw new FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada(TipoLancamentoEnum.DESPESA.getDescricao());
        if (fluxoCaixaParametro.getRendaCategoriaPadrao() == null) throw new FluxoCaixaConfigException.CategoriaPadraoNaoEncontrada(TipoLancamentoEnum.RENDA.getDescricao());
    }

}
