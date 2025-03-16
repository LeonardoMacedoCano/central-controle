package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.UsuarioFormDTO;
import br.com.lcano.centraldecontrole.exception.UsuarioException;
import br.com.lcano.centraldecontrole.repository.UsuarioRepository;
import br.com.lcano.centraldecontrole.util.UsuarioUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioUtil usuarioUtil;
    private final ArquivoService arquivoService;
    private final TemaService temaService;

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioException.UsuarioNaoEncontrado::new);
    }

    @Transactional
    public void updateAsDto(UsuarioFormDTO usuarioFormDTO) throws Exception {
        Usuario usuario = usuarioUtil.getUsuarioAutenticado();
        String currentPassword = usuarioFormDTO.getCurrentPassword();
        String newPassword = usuarioFormDTO.getNewPassword();

        if (shouldUpdatePassword(currentPassword, newPassword)) {
            validateSenhas(usuario, currentPassword, newPassword);
            updateSenha(usuario, newPassword);
        }

        if (usuarioFormDTO.getIdTema() != null && usuarioFormDTO.getIdTema() > 0) {
            updateTema(usuario, usuarioFormDTO.getIdTema());
        }

        MultipartFile file = usuarioFormDTO.getFile();
        if (file != null && !file.isEmpty()) {
            updateArquivo(usuario, file);
        }

        usuarioRepository.save(usuario);
    }

    private boolean shouldUpdatePassword(String currentPassword, String newPassword) {
        return currentPassword != null && newPassword != null &&
                !currentPassword.isBlank() && !newPassword.isBlank();
    }

    private void validateSenhas(Usuario usuario, String currentPassword, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(currentPassword, usuario.getSenha())) {
            throw new UsuarioException.CredenciaisInvalidas();
        }

        if (currentPassword.equals(newPassword)) {
            throw new UsuarioException.SenhaAtualIncorreta();
        }
    }

    private void updateSenha(Usuario usuario, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(passwordEncoder.encode(newPassword));
    }

    private void updateTema(Usuario usuario, Long idTema) {
        usuario.setTema(temaService.findById(idTema));
    }

    private void updateArquivo(Usuario usuario, MultipartFile file) throws Exception {
        Arquivo arquivo = arquivoService.uploadArquivoOrGetExists(file);
        usuario.setArquivo(arquivo);
    }
}
