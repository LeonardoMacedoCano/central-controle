package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.UsuarioRequestDTO;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.UsuarioRepository;
import com.backend.centraldecontrole.util.CustomException;
import com.backend.centraldecontrole.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public void cadastrarUsuario(UsuarioRequestDTO data) {
        if (usuarioJaCadastrado(data.username())) {
            throw new CustomException.UsuarioJaCadastradoException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.username(), encryptedPassword, DateUtil.getDataAtual());
        this.usuarioRepository.save(novoUsuario);
    }

    public boolean usuarioJaCadastrado(String username) {
        return loadUserByUsername(username) != null;
    }
}