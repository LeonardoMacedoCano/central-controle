package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.DespesaCategoriaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaRegraDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ReceitaCategoriaDTO;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.FluxoCaixaConfigException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ExtratoContaRegraRepository;
import br.com.lcano.centraldecontrole.service.AbstractGenericService;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExtratoContaRegraService extends AbstractGenericService<ExtratoContaRegra, Long> {
    @Autowired
    private final ExtratoContaRegraRepository repository;
    @Autowired
    private final UsuarioUtil usuarioUtil;
    @Autowired
    private final FluxoCaixaConfigService fluxoCaixaConfigService;

    @Override
    protected JpaRepository<ExtratoContaRegra, Long> getRepository() {
        return repository;
    }

    @Override
    protected ExtratoContaRegraDTO getDtoInstance() {
        return new ExtratoContaRegraDTO();
    }

    @Override
    public List<ExtratoContaRegraDTO> findAllAsDto() {
        return this.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuarioUtil.getUsuarioAutenticado())
                .stream()
                .map(item -> new ExtratoContaRegraDTO().fromEntity(item))
                .toList();
    }

    public List<ExtratoContaRegra> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario) {
        return repository.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario, true);
    }

    public void validateAndSave(ExtratoContaRegraDTO extratoContaRegraDTO) {
        validatePrioridadeUnica(extratoContaRegraDTO);
        this.save(this.buildExtratoContaRegra(extratoContaRegraDTO));
    }

    private void validatePrioridadeUnica(ExtratoContaRegraDTO dto) {
        boolean prioridadeExistente = repository.existsByUsuarioAndPrioridadeAndIdNot(
                usuarioUtil.getUsuarioAutenticado(),
                dto.getPrioridade(),
                dto.getId());

        if (prioridadeExistente) throw new FluxoCaixaConfigException.UniquePrioridadeViolada(dto.getPrioridade());
    }

    private ExtratoContaRegra buildExtratoContaRegra(ExtratoContaRegraDTO dto) {
        ExtratoContaRegra extratoContaRegra = dto.toEntity();
        extratoContaRegra.setUsuario(usuarioUtil.getUsuarioAutenticado());
        return extratoContaRegra;
    }

    public DespesaCategoriaDTO getDespesaCategoriaPadrao() {
        return fluxoCaixaConfigService.getDespesaCategoriaPadrao();
    }

    public ReceitaCategoriaDTO getReceitaCategoriaPadrao() {
        return fluxoCaixaConfigService.getReceitaCategoriaPadrao();
    }
}
