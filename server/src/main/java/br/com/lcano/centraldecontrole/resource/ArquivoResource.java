package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.service.ArquivoService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/arquivo")
public class ArquivoResource {

    @Autowired
    private final ArquivoService service;

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getArquivoById(@PathVariable Long id) {
        return service.findById(id)
                .map(arquivo -> {
                    ByteArrayResource resource = new ByteArrayResource(arquivo.getConteudo());
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + arquivo.getNome())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadArquivo(@RequestParam("file") MultipartFile file) throws Exception {
        Arquivo arquivo = service.uploadArquivo(file);
        return CustomSuccess.buildResponseEntity(String.format("Arquivo salvo com sucesso! ID: %d", arquivo.getId()));
    }
}
