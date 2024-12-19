package br.com.lcano.centraldecontrole.service.fluxocaixa;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.fluxocaixa.ExtratoContaRegra;
import br.com.lcano.centraldecontrole.dto.CategoriaDTO;
import br.com.lcano.centraldecontrole.dto.fluxocaixa.ExtratoContaRegraDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.enums.fluxocaixa.TipoRegraExtratoConta;
import br.com.lcano.centraldecontrole.exception.fluxocaixa.ExtratoException;
import br.com.lcano.centraldecontrole.repository.fluxocaixa.ExtratoContaRegraRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExtratoContaRegraService {
    @Autowired
    private final ExtratoContaRegraRepository extratoContaRegraRepository;
    @Autowired
    private final UsuarioUtil usuarioUtil;
    @Autowired
    private final FluxoCaixaConfigService fluxoCaixaConfigService;

    public List<ExtratoContaRegra> findByUsuarioAndAtivoOrderByPrioridadeAsc(Usuario usuario) {
        return extratoContaRegraRepository.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuario, true);
    }

    public List<ExtratoContaRegraDTO> getRegras() {
        return this.findByUsuarioAndAtivoOrderByPrioridadeAsc(usuarioUtil.getUsuarioAutenticado())
                .stream()
                .map(ExtratoContaRegraDTO::converterParaDTO)
                .toList();
    }


    public void saveRegra(ExtratoContaRegraDTO extratoContaRegraDTO) {
        validarPrioridadeUnica(extratoContaRegraDTO);
        extratoContaRegraRepository.save(this.buildExtratoContaRegra(extratoContaRegraDTO));
    }

    private void validarPrioridadeUnica(ExtratoContaRegraDTO dto) {
        boolean prioridadeExistente = extratoContaRegraRepository.existsByUsuarioAndPrioridadeAndIdNot(
                usuarioUtil.getUsuarioAutenticado(),
                dto.getPrioridade(),
                dto.getId());

        if (prioridadeExistente) throw new ExtratoException.ExtratoContaRegraUniquePrioridadeViolada(dto.getPrioridade());
    }

    private ExtratoContaRegra buildExtratoContaRegra(ExtratoContaRegraDTO dto) {
        ExtratoContaRegra extratoContaRegra = new ExtratoContaRegra();

        extratoContaRegra.setId(dto.getId());
        extratoContaRegra.setUsuario(usuarioUtil.getUsuarioAutenticado());
        extratoContaRegra.setTipoRegra(TipoRegraExtratoConta.valueOf(dto.getTipoRegra()));
        extratoContaRegra.setDescricaoMatch(dto.getDescricaoMatch());
        extratoContaRegra.setDescricaoDestino(dto.getDescricaoDestino());
        extratoContaRegra.setIdCategoria(dto.getIdCategoria());
        extratoContaRegra.setPrioridade(dto.getPrioridade());
        extratoContaRegra.setAtivo(dto.isAtivo());

        return extratoContaRegra;
    }

    public CategoriaDTO getCategoriaPadrao(TipoLancamentoEnum tipoLancamentoEnum) {
        CategoriaDTO categoriaDTO;

        if (TipoLancamentoEnum.RECEITA.equals(tipoLancamentoEnum)) {
            categoriaDTO = fluxoCaixaConfigService.getReceitaCategoriaPadrao();
        } else {
            categoriaDTO = fluxoCaixaConfigService.getDespesaCategoriaPadrao();
        }

        if (categoriaDTO == null) throw new ExtratoException.ExtratoContaRegraCategoriaPadraoNaoEncontrada(tipoLancamentoEnum.getDescricao());

        return categoriaDTO;
    }
}
