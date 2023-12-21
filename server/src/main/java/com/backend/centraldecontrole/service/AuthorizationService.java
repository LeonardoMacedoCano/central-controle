package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.UsuarioRequestDTO;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.TimeZone;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public void cadastrarUsuario(UsuarioRequestDTO data) {
        if (loadUserByUsername(data.username()) != null) {
            throw new UsernameNotFoundException("Usuário já cadastrado!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.username(), encryptedPassword, getDataAtual());

        this.usuarioRepository.save(novoUsuario);
    }

    private Date getDataAtual() {
        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        Date currentDate = new Date();
        currentDate.setTime(currentDate.getTime() + timeZone.getRawOffset());
        return currentDate;
    }
}