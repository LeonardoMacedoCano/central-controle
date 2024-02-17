package br.com.lcano.centraldecontrole.controller;

import br.com.lcano.centraldecontrole.dto.IdeiaRequestDTO;
import br.com.lcano.centraldecontrole.dto.IdeiaResponseDTO;
import br.com.lcano.centraldecontrole.model.Usuario;
import br.com.lcano.centraldecontrole.service.IdeiaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("ideia")
public class IdeiaController {
    @Autowired
    private IdeiaService ideiaService;

    @PostMapping("/add")
    public ResponseEntity<Object> adicionarIdeia(@RequestBody IdeiaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return ideiaService.adicionarIdeia(data, usuario);
    }

    @PutMapping("/editar/{idIdeia}")
    public ResponseEntity<Object> editarIdeia(@PathVariable Long idIdeia, @RequestBody IdeiaRequestDTO data, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return ideiaService.editarIdeia(idIdeia, data, usuario);
    }

    @DeleteMapping("/excluir/{idIdeia}")
    public ResponseEntity<Object> excluirIdeia(@PathVariable Long idIdeia) {
        return ideiaService.excluirIdeia(idIdeia);
    }

    @GetMapping("/listar")
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
