package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.UsuarioConfig;
import br.com.lcano.centraldecontrole.dto.UsuarioConfigDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioConfigService {
    @Autowired
    private final UsuarioConfigRepository usuarioConfigRepository;

    public UsuarioConfigService(UsuarioConfigRepository usuarioConfigRepository) {
        this.usuarioConfigRepository = usuarioConfigRepository;
    }

    public void salvarUsuarioConfig(UsuarioConfig usuarioConfig) {
        usuarioConfigRepository.save(usuarioConfig);
    }

    public UsuarioConfig getUsuarioConfigById(Long id) {
        return usuarioConfigRepository.findById(id)
                .orElseThrow(() -> new UsuarioException.UsuarioConfigNaoEncontradoById(id));
    }

    public UsuarioConfig getUsuarioConfigByUsuario(Usuario usuario) {
        return usuarioConfigRepository.findByUsuario(usuario)
                .orElseThrow(UsuarioException.UsuarioConfigNaoEncontrado::new);
    }

    @Transactional
    public void gerarUsuarioConfig(Usuario usuario) {
        UsuarioConfig novoUsuarioConfig = new UsuarioConfig(usuario);
        salvarUsuarioConfig(novoUsuarioConfig);
    }

    @Transactional
    public void editarUsuarioConfig(Long idUsuarioConfig, UsuarioConfigDTO data) {
        UsuarioConfig usuarioConfigExistente = getUsuarioConfigById(idUsuarioConfig);
        usuarioConfigExistente.setDespesaNumeroItemPagina(data.getDespesaNumeroItemPagina());
        usuarioConfigExistente.setDespesaValorMetaMensal(data.getDespesaValorMetaMensal());
        usuarioConfigExistente.setDespesaDiaPadraoVencimento(data.getDespesaDiaPadraoVencimento());
        usuarioConfigExistente.setDespesaFormaPagamentoPadrao(data.getDespesaFormaPagamentoPadrao());

        salvarUsuarioConfig(usuarioConfigExistente);
    }
}
