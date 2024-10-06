package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioConfigRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioConfigService {
    @Autowired
    private final UsuarioConfigRepository usuarioConfigRepository;

    public UsuarioConfig getUsuarioConfigById(Long id) {
        return this.usuarioConfigRepository.findById(id)
                .orElseThrow(() -> new UsuarioException.UsuarioConfigNaoEncontradoById(id));
    }

    public UsuarioConfig getUsuarioConfigByIdUsuario(Long idUsuario) {
        return this.usuarioConfigRepository.findByUsuarioId(idUsuario)
                .orElseThrow(UsuarioException.UsuarioConfigNaoEncontrado::new);
    }

    @Transactional
    public void createAndSaveUsuarioConfig(Usuario usuario) {
        this.usuarioConfigRepository.save(new UsuarioConfig(usuario));
    }

    @Transactional
    public void editarUsuarioConfig(Long idUsuarioConfig, UsuarioConfigDTO data) {
        UsuarioConfig usuarioConfigExistente = getUsuarioConfigById(idUsuarioConfig);
        usuarioConfigExistente.setDespesaNumeroMaxItemPagina(data.getDespesaNumeroMaxItemPagina());
        usuarioConfigExistente.setDespesaValorMetaMensal(data.getDespesaValorMetaMensal());
        usuarioConfigExistente.setDespesaDiaPadraoVencimento(data.getDespesaDiaPadraoVencimento());

        this.usuarioConfigRepository.save(usuarioConfigExistente);
    }
}
