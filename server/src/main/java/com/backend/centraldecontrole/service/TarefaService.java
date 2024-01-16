package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.TarefaRequestDTO;
import com.backend.centraldecontrole.dto.TarefaResponseDTO;
import com.backend.centraldecontrole.model.*;
import com.backend.centraldecontrole.repository.CategoriaTarefaRepository;
import com.backend.centraldecontrole.repository.TarefaRepository;
import com.backend.centraldecontrole.util.CustomException;
import com.backend.centraldecontrole.util.CustomSuccess;
import com.backend.centraldecontrole.util.MensagemConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private CategoriaTarefaRepository categoriaTarefaRepository;

    public ResponseEntity<Object> adicionarTarefa(TarefaRequestDTO data, Usuario usuario) {
        return getCategoriaPorId(data.idCategoria())
                .map(categoria -> {
                    Tarefa novaTarefa = new Tarefa(usuario, categoria, data.titulo(), data.descricao(), data.dataPrazo(), data.finalizado());
                    salvarTarefa(novaTarefa);
                    return CustomSuccess.buildResponseEntity(MensagemConstantes.TAREFA_ADICIONADA_COM_SUCESSO);
                })
                .orElseThrow(() -> new CustomException.CategoriaTarefaNaoEncontradaComIdException(data.idCategoria()));
    }

    public ResponseEntity<Object> editarTarefa(Long idTarefa, TarefaRequestDTO data, Usuario usuario) {
        return tarefaRepository.findById(idTarefa)
                .map(tarefaExistente -> getCategoriaPorId(data.idCategoria())
                        .map(categoria -> {
                            tarefaExistente.setUsuario(usuario);
                            tarefaExistente.setCategoria(categoria);
                            tarefaExistente.setTitulo(data.titulo());
                            tarefaExistente.setDescricao(data.descricao());
                            tarefaExistente.setDataPrazo(data.dataPrazo());
                            tarefaExistente.setFinalizado(data.finalizado());
                            salvarTarefa(tarefaExistente);
                            return CustomSuccess.buildResponseEntity(MensagemConstantes.TAREFA_EDITADA_COM_SUCESSO);
                        })
                        .orElseThrow(() -> new CustomException.CategoriaTarefaNaoEncontradaComIdException(data.idCategoria())))
                .orElseThrow(() -> new CustomException.TarefaNaoEncontradaComIdException(idTarefa));
    }

    public ResponseEntity<Object> excluirTarefa(Long idTarefa) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(idTarefa);

        if (tarefaOptional.isPresent()) {
            tarefaRepository.delete(tarefaOptional.get());
            return CustomSuccess.buildResponseEntity(MensagemConstantes.TAREFA_EXCLUIDA_COM_SUCESSO);
        } else {
            throw new CustomException.TarefaNaoEncontradaComIdException(idTarefa);
        }
    }

    public List<TarefaResponseDTO> listarTarefasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
        if (idUsuario == null) {
            throw new CustomException.UsuarioNaoEncontradoException();
        }

        List<Tarefa> tarefas;

        if (ano != null && mes != null) {
            LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1);
            LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());

            Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

            tarefas = tarefaRepository.findByUsuarioIdAndDataPrazoBetween(idUsuario, dataInicio, dataFim);
        } else {
            tarefas = tarefaRepository.findByUsuarioId(idUsuario);
        }

        return tarefas.stream()
                .map(this::converterParaTarefaResponseDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    }

    public Optional<CategoriaTarefa> getCategoriaPorId(Long id) {
        return categoriaTarefaRepository.findById(id);
    }

    private TarefaResponseDTO converterParaTarefaResponseDTO(Tarefa tarefa) {
        if (tarefa == null || tarefa.getCategoria() == null) {
            return null;
        }

        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getCategoria().getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getDataPrazo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                tarefa.isFinalizado()
        );
    }
}
