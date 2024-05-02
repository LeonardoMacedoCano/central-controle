package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.IdeiaRequestDTO;
import br.com.lcano.centraldecontrole.dto.IdeiaResponseDTO;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.service.IdeiaService;
import br.com.lcano.centraldecontrole.util.CustomSuccess;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ideia")
public class IdeiaResource {
    @Autowired
    private final IdeiaService ideiaService;

    public IdeiaResource(IdeiaService ideiaService) {
        this.ideiaService = ideiaService;
    }

    @PostMapping
    public ResponseEntity<Object> gerarIdeia(@RequestBody IdeiaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        ideiaService.gerarIdeia(data, usuario);
        return CustomSuccess.buildResponseEntity("Ideia adicionada com sucesso.");
    }

    @PutMapping("/{idIdeia}")
    public ResponseEntity<Object> editarIdeia(@PathVariable Long idIdeia, @RequestBody IdeiaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        ideiaService.editarIdeia(idIdeia, data, usuario);
        return CustomSuccess.buildResponseEntity("Ideia editada com sucesso.");
    }

    @DeleteMapping("/{idIdeia}")
    public ResponseEntity<Object> excluirIdeia(@PathVariable Long idIdeia) {
        ideiaService.excluirIdeia(idIdeia);
        return CustomSuccess.buildResponseEntity("Ideia excluída com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<IdeiaResponseDTO>> listarIdeiasDoUsuario(
            HttpServletRequest request,
            @RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "mes", required = false) Integer mes
    ) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        List<IdeiaResponseDTO> IdeiasDTO = ideiaService.listarIdeiasDoUsuario(usuario.getId(), ano, mes);
        return ResponseEntity.ok(IdeiasDTO);
    }
}
