package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.DespesaRequestDTO;
import com.backend.centraldecontrole.dto.DespesaResponseDTO;
import com.backend.centraldecontrole.model.CategoriaDespesa;
import com.backend.centraldecontrole.model.Despesa;
import com.backend.centraldecontrole.model.Usuario;
import com.backend.centraldecontrole.repository.CategoriaDespesaRepository;
import com.backend.centraldecontrole.repository.DespesaRepository;
import com.backend.centraldecontrole.util.CustomException;
import com.backend.centraldecontrole.util.CustomSuccess;
import com.backend.centraldecontrole.util.MensagemConstantes;
import org.springframework.http.ResponseEntity;
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
    private DespesaRepository despesaRepository;
    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    public ResponseEntity<Object> adicionarDespesa(DespesaRequestDTO data, Usuario usuario) {
        return getCategoriaPorId(data.idCategoria())
            .map(categoria -> {
                Despesa novaDespesa = new Despesa(usuario, categoria, data.descricao(), data.valor(), data.data());
                salvarDespesa(novaDespesa);
                return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_ADICIONADA_COM_SUCESSO);
            })
            .orElseThrow(() -> new CustomException.CategoriaDespesaNaoEncontradaComIdException(data.idCategoria()));
    }


    public ResponseEntity<Object> editarDespesa(Long idDespesa, DespesaRequestDTO data, Usuario usuario) {
        return despesaRepository.findById(idDespesa)
            .map(despesaExistente -> getCategoriaPorId(data.idCategoria())
                .map(categoria -> {
                    despesaExistente.setCategoria(categoria);
                    despesaExistente.setDescricao(data.descricao());
                    despesaExistente.setValor(data.valor());
                    despesaExistente.setData(data.data());
                    despesaExistente.setUsuario(usuario);
                    salvarDespesa(despesaExistente);
                    return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_EDITADA_COM_SUCESSO);
                })
                .orElseThrow(() -> new CustomException.CategoriaDespesaNaoEncontradaComIdException(data.idCategoria())))
            .orElseThrow(() -> new CustomException.DespesaNaoEncontradaComIdException(idDespesa));
    }

    public ResponseEntity<Object> excluirDespesa(Long idDespesa) {
        Optional<Despesa> despesaOptional = despesaRepository.findById(idDespesa);

        if (despesaOptional.isPresent()) {
            despesaRepository.delete(despesaOptional.get());
            return CustomSuccess.buildResponseEntity(MensagemConstantes.DESPESA_EXCLUIDA_COM_SUCESSO);
        } else {
            throw new CustomException.DespesaNaoEncontradaComIdException(idDespesa);
        }
    }

    public List<DespesaResponseDTO> listarDespesasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
        if (idUsuario == null) {
            throw new CustomException.UsuarioNaoEncontradoException();
        }

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
