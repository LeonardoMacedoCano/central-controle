package br.com.lcano.centraldecontrole.config;

import br.com.lcano.centraldecontrole.dto.UsuarioDTO;
import br.com.lcano.centraldecontrole.service.AuthorizationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {
    @Autowired
    AuthorizationService authorizationService;
    @Value("${spring.jackson.time-zone}")
    String timeZone;
    @Value("${spring.jackson.date-format}")
    String dateFormat;
    @Value("${admin.username}")
    String adminUsername;
    @Value("${admin.password}")
    String adminPassword;

    private static String staticTimeZone;
    private static String staticDateFormat;

    @PostConstruct
    public void init() {
        PropertiesConfig.staticTimeZone = this.timeZone;
        PropertiesConfig.staticDateFormat = this.dateFormat;

        if (!adminUserJaCadastrado()) {
            try {
                registerAdminUser();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao registrar o usuário admin", e);
            }
        }
    }

    private Boolean adminUserJaCadastrado() {
        return authorizationService.usuarioJaCadastrado(adminUsername);
    }

    private void registerAdminUser() {
        authorizationService.register(new UsuarioDTO(adminUsername, adminPassword));
    }

    public static String getTimeZone() {
        return staticTimeZone;
    }

    public static String getDateFormat() {
        return staticDateFormat;
    }
}