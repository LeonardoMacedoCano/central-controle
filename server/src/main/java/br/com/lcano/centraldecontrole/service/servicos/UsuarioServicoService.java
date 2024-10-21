package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.domain.servicos.UsuarioServico;
import br.com.lcano.centraldecontrole.repository.servicos.UsuarioServicoRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioServicoService {
    @Autowired
    private final UsuarioServicoRepository usuarioServicoRepository;
    @Autowired
    UsuarioUtil usuarioUtil;

    public boolean hasPermissionForService(Long idServico) {
        Usuario usuarioAutenticado = usuarioUtil.getUsuarioAutenticado();
        UsuarioServico usuarioServico = usuarioServicoRepository.findByUsuarioIdAndServicoId(usuarioAutenticado.getId(), idServico);
        return usuarioServico != null && usuarioServico.isPermissao();
    }
}
