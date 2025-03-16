package br.com.lcano.centraldecontrole.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFormDTO {
    private String username;
    private String currentPassword;
    private String newPassword;
    private Long idTema;
    private MultipartFile file;
}
