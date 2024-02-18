package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.DespesaRequestDTO;
import br.com.lcano.centraldecontrole.dto.DespesaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.DespesaException;
import br.com.lcano.centraldecontrole.repository.CategoriaDespesaRepository;
import br.com.lcano.centraldecontrole.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DespesaService {
    @Autowired
    private final DespesaRepository despesaRepository;
    @Autowired
    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public DespesaService(DespesaRepository despesaRepository, CategoriaDespesaRepository categoriaDespesaRepository) {
        this.despesaRepository = despesaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    @Transactional
    public void gerarDespesa(DespesaRequestDTO data, Usuario usuario) {
        CategoriaDespesa categoria = getCategoriaPorId(data.idCategoria())
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(data.idCategoria()));

        Despesa novaDespesa = new Despesa(usuario, categoria, data.descricao(), data.valor(), data.data());
        salvarDespesa(novaDespesa);
    }

    @Transactional
    public void editarDespesa(Long idDespesa, DespesaRequestDTO data, Usuario usuario) {
        Despesa despesaExistente = despesaRepository.findById(idDespesa)
            .orElseThrow(() -> new DespesaException.DespesaNaoEncontradaById(idDespesa));

        CategoriaDespesa categoria = getCategoriaPorId(data.idCategoria())
            .orElseThrow(() -> new DespesaException.CategoriaDespesaNaoEncontradaById(data.idCategoria()));

        despesaExistente.setCategoria(categoria);
        despesaExistente.setDescricao(data.descricao());
        despesaExistente.setValor(data.valor());
        despesaExistente.setData(data.data());
        despesaExistente.setUsuario(usuario);

        salvarDespesa(despesaExistente);
    }

    @Transactional
    public void excluirDespesa(Long idDespesa) {
        despesaRepository.findById(idDespesa)
            .ifPresentOrElse(
                    despesaRepository::delete,
                () -> {
                    throw new DespesaException.DespesaNaoEncontradaById(idDespesa);
            });
    }

    public List<DespesaResponseDTO> listarDespesasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
        List<Despesa> despesas;

        if (ano != null && mes != null) {
            LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1);
            LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());

            Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

            despesas = despesaRepository.findByUsuarioIdAndDataBetween(idUsuario, dataInicio, dataFim);
        } else {
            despesas = despesaRepository.findByUsuarioId(idUsuario);
        }

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
