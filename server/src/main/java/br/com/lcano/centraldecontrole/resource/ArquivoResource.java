package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.service.ArquivoService;
import lombok.AllArgsConstructor;
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

    private final ArquivoService arquivoService;

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getArquivoById(@PathVariable Long id) {
        return arquivoService.findById(id)
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
    public ResponseEntity<String> uploadArquivo(@RequestParam("file") MultipartFile file) {
        try {
            Arquivo arquivo = arquivoService.uploadArquivo(file);
            return new ResponseEntity<>("Arquivo salvo com sucesso! ID: " + arquivo.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar o arquivo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
