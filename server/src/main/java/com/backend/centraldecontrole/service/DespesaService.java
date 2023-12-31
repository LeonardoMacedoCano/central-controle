package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.DespesaRequestDTO;
import com.backend.centraldecontrole.dto.DespesaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaDespesa;
import com.backend.centraldecontrole.model.Despesa;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import com.backend.centraldecontrole.repository.DespesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService {
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    public ResponseEntity<String> adicionarDespesa(DespesaRequestDTO data, Usuario usuario) {
        return getCategoriaPorId(data.idCategoria())
                .map(categoria -> {
                    Despesa novaDespesa = new Despesa(usuario, categoria, data.descricao(), data.valor(), data.data());
                    salvarDespesa(novaDespesa);
                    return ResponseEntity.ok("Despesa adicionada com sucesso!");
                })
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Categoria de despesa não encontrada com o id " + data.idCategoria()));
    }

    public ResponseEntity<String> editarDespesa(Long idDespesa, DespesaRequestDTO data, Usuario usuario) {
        return despesaRepository.findById(idDespesa)
            .map(despesaExistente -> {
                return getCategoriaPorId(data.idCategoria())
                        .map(categoria -> {
                            despesaExistente.setCategoria(categoria);
                            despesaExistente.setDescricao(data.descricao());
                            despesaExistente.setValor(data.valor());
                            despesaExistente.setData(data.data());
                            despesaExistente.setUsuario(usuario);

                            salvarDespesa(despesaExistente);

                            return ResponseEntity.ok("Despesa editada com sucesso!");
                        })
                        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Categoria de despesa não encontrada com o id " + data.idCategoria()));
            })
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Despesa não encontrada com o id " + idDespesa));
    }

    public ResponseEntity<String> excluirDespesa(Long idDespesa) {
        Optional<Despesa> despesaOptional = despesaRepository.findById(idDespesa);

        if (despesaOptional.isPresent()) {
            despesaRepository.delete(despesaOptional.get());
            return ResponseEntity.ok("Despesa excluída com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Despesa não encontrada com o id " + idDespesa);
        }
    }

    public List<DespesaResponseDTO> listarDespesasDoUsuario(Long idUsuario) {
        List<Despesa> despesas = despesaRepository.findByUsuarioId(idUsuario);
        return despesas.stream()
                .map(this::converterParaDespesaResponseDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void salvarDespesa(Despesa despesa) {
        despesaRepository.save(despesa);
    }

    public Optional<CategoriaDespesa> getCategoriaPorId(Long id) {
        return categoriaDespesaRepository.findById(id);
    }

    private DespesaResponseDTO converterParaDespesaResponseDTO(Despesa despesa) {
        if (despesa == null || despesa.getCategoria() == null) {
            return null;
        }

        LocalDateTime dataLocalDateTime = despesa.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new DespesaResponseDTO(
                despesa.getId(),
                despesa.getCategoria().getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                dataLocalDateTime
        );
    }
}
