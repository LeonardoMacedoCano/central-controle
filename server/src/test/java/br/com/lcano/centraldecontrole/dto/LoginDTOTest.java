package br.com.lcano.centraldecontrole.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDTOTest {

    @Test
    void testConstructorAndGetters() {
        String username = "testUser";
        String token = "testToken";

        LoginDTO loginDTO = new LoginDTO(username, token);

        assertNotNull(loginDTO.getUsuario());
        assertEquals(username, loginDTO.getUsuario().getUsername());
        assertEquals(token, loginDTO.getUsuario().getToken());
    }
}
