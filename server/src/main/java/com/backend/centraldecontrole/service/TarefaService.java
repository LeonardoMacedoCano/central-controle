package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.TarefaResponseDTO;
import com.backend.centraldecontrole.model.Tarefa;
import com.backend.centraldecontrole.repository.CategoriaTarefaRepository;
import com.backend.centraldecontrole.repository.TarefaRepository;
import com.backend.centraldecontrole.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private CategoriaTarefaRepository categoriaTarefaRepository;

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

            tarefas = tarefaRepository.findByUsuarioIdAndDataInclusaoBetween(idUsuario, dataInicio, dataFim);
        } else {
            tarefas = tarefaRepository.findByUsuarioId(idUsuario);
        }

        return tarefas.stream()
                .map(this::converterParaTarefaResponseDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private TarefaResponseDTO converterParaTarefaResponseDTO(Tarefa tarefa) {
        if (tarefa == null || tarefa.getCategoria() == null) {
            return null;
        }

        LocalDateTime dataInclusao = tarefa.getDataInclusao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dataPrazo = tarefa.getDataPrazo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getCategoria().getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                dataInclusao,
                dataPrazo,
                tarefa.isFinalizado()
        );
    }
}
