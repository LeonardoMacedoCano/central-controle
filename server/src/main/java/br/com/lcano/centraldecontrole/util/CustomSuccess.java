package br.com.lcano.centraldecontrole.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class CustomSuccess {
    public static ResponseEntity<Object> buildResponseEntity(String mensagem) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", mensagem));
    }
}
