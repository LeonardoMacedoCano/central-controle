package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.DespesaCategoria;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.RegraExtratoContaCorrente;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ReceitaCategoria;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.RegraExtratoContaCorrenteDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.FluxoCaixaConfigException;
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
    private final FluxoCaixaConfigService fluxoCaixaConfigService;

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

    public void validateAndSave(RegraExtratoContaCorrenteDTO extratoContaRegraDTO) {
        validatePrioridadeUnica(extratoContaRegraDTO);
        this.save(this.buildExtratoContaRegra(extratoContaRegraDTO));
    }

    private void validatePrioridadeUnica(RegraExtratoContaCorrenteDTO dto) {
        boolean prioridadeExistente = repository.existsByUsuarioAndPrioridadeAndIdNot(
                usuarioUtil.getUsuarioAutenticado(),
                dto.getPrioridade(),
                dto.getId());

        if (prioridadeExistente) throw new FluxoCaixaConfigException.UniquePrioridadeViolada(dto.getPrioridade());
    }

    private RegraExtratoContaCorrente buildExtratoContaRegra(RegraExtratoContaCorrenteDTO dto) {
        RegraExtratoContaCorrente extratoContaRegra = dto.toEntity();
        extratoContaRegra.setUsuario(usuarioUtil.getUsuarioAutenticado());
        return extratoContaRegra;
    }

    public DespesaCategoria getDespesaCategoriaPadrao() {
        return fluxoCaixaConfigService.getDespesaCategoriaPadrao();
    }

    public ReceitaCategoria getReceitaCategoriaPadrao() {
        return fluxoCaixaConfigService.getReceitaCategoriaPadrao();
    }
}
