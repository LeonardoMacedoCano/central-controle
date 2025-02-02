package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.RegraExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.RegraExtratoContaCorrenteRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RegraExtratoContaCorrenteService extends AbstractGenericService<RegraExtratoContaCorrente, Long> {
    @Autowired
    private final RegraExtratoContaCorrenteRepository repository;
    @Autowired
    private final UsuarioUtil usuarioUtil;
    @Autowired
    private final FluxoCaixaParametroService fluxoCaixaParametroService;

    @Override
    protected JpaRepository<RegraExtratoContaCorrente, Long> getRepository() {
        return repository;
    }

    @Override
    protected RegraExtratoContaCorrenteDTO getDtoInstance() {
        return new RegraExtratoContaCorrenteDTO();
    }

    @Override
    public List<RegraExtratoContaCorrenteDTO> findAllAsDto() {
        return this.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuarioUtil.getUsuarioAutenticado())
                .stream()
                .map(item -> new RegraExtratoContaCorrenteDTO().fromEntity(item))
                .toList();
    }

    public List<RegraExtratoContaCorrente> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario) {
        return repository.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario, true);
    }

    public DespesaCategoria getDespesaCategoriaPadrao() {
        return fluxoCaixaParametroService.getDespesaCategoriaPadrao();
    }

    public ReceitaCategoria getReceitaCategoriaPadrao() {
        return fluxoCaixaParametroService.getReceitaCategoriaPadrao();
    }
}
