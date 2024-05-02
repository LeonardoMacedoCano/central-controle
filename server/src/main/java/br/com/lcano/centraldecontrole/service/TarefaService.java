package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.TarefaRequestDTO;
import br.com.lcano.centraldecontrole.dto.TarefaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaTarefa;
import br.com.lcano.centraldecontrole.domain.Tarefa;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.TarefaException;
import br.com.lcano.centraldecontrole.repository.CategoriaTarefaRepository;
import br.com.lcano.centraldecontrole.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TarefaRepository tarefaRepository;
    @Autowired
    private final CategoriaTarefaRepository categoriaTarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository, CategoriaTarefaRepository categoriaTarefaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaTarefaRepository = categoriaTarefaRepository;
    }

    @Transactional
    public void gerarTarefa(TarefaRequestDTO data, Usuario usuario) {
        CategoriaTarefa categoria = getCategoriaPorId(data.idCategoria())
                .orElseThrow(() -> new TarefaException.CategoriaTarefaNaoEncontradaById(data.idCategoria()));

        Tarefa novaTarefa = new Tarefa(usuario, categoria, data.titulo(), data.descricao(), data.dataPrazo(), data.finalizado());
        salvarTarefa(novaTarefa);
    }

    @Transactional
    public void editarTarefa(Long idTarefa, TarefaRequestDTO data, Usuario usuario) {
        Tarefa tarefaExistente = tarefaRepository.findById(idTarefa)
            .orElseThrow(() -> new TarefaException.TarefaNaoEncontradaById(idTarefa));

        CategoriaTarefa categoria = getCategoriaPorId(data.idCategoria())
            .orElseThrow(() -> new TarefaException.CategoriaTarefaNaoEncontradaById(data.idCategoria()));

        tarefaExistente.setUsuario(usuario);
        tarefaExistente.setCategoria(categoria);
        tarefaExistente.setTitulo(data.titulo());
        tarefaExistente.setDescricao(data.descricao());
        tarefaExistente.setDataPrazo(data.dataPrazo());
        tarefaExistente.setFinalizado(data.finalizado());

        salvarTarefa(tarefaExistente);
    }

    @Transactional
    public void excluirTarefa(Long idTarefa) {
        tarefaRepository.findById(idTarefa)
            .ifPresentOrElse(
                    tarefaRepository::delete,
                () -> {
                    throw new TarefaException.TarefaNaoEncontradaById(idTarefa);
                }
            );
    }

    public List<TarefaResponseDTO> listarTarefasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
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
