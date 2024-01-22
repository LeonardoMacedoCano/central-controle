package com.backend.centraldecontrole.service;

import com.backend.centraldecontrole.dto.IdeiaRequestDTO;
import com.backend.centraldecontrole.dto.IdeiaResponseDTO;
import com.backend.centraldecontrole.model.*;
import com.backend.centraldecontrole.repository.CategoriaIdeiaRepository;
import com.backend.centraldecontrole.repository.IdeiaRepository;
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
public class IdeiaService {
    @Autowired
    private IdeiaRepository ideiaRepository;
    @Autowired
    private CategoriaIdeiaRepository categoriaIdeiaRepository;

    public ResponseEntity<Object> adicionarIdeia(IdeiaRequestDTO data, Usuario usuario) {
        return getCategoriaPorId(data.idCategoria())
                .map(categoria -> {
                    Ideia novaIdeia = new Ideia(usuario, categoria, data.titulo(), data.descricao(), data.dataPrazo(), data.finalizado());
                    salvarIdeia(novaIdeia);
                    return CustomSuccess.buildResponseEntity(MensagemConstantes.IDEIA_ADICIONADA_COM_SUCESSO);
                })
                .orElseThrow(() -> new CustomException.CategoriaIdeiaNaoEncontradaComIdException(data.idCategoria()));
    }

    public ResponseEntity<Object> editarIdeia(Long idIdeia, IdeiaRequestDTO data, Usuario usuario) {
        return ideiaRepository.findById(idIdeia)
                .map(IdeiaExistente -> getCategoriaPorId(data.idCategoria())
                        .map(categoria -> {
                            IdeiaExistente.setUsuario(usuario);
                            IdeiaExistente.setCategoria(categoria);
                            IdeiaExistente.setTitulo(data.titulo());
                            IdeiaExistente.setDescricao(data.descricao());
                            IdeiaExistente.setDataPrazo(data.dataPrazo());
                            IdeiaExistente.setFinalizado(data.finalizado());
                            salvarIdeia(IdeiaExistente);
                            return CustomSuccess.buildResponseEntity(MensagemConstantes.IDEIA_EDITADA_COM_SUCESSO);
                        })
                        .orElseThrow(() -> new CustomException.CategoriaIdeiaNaoEncontradaComIdException(data.idCategoria())))
                .orElseThrow(() -> new CustomException.IdeiaNaoEncontradaComIdException(idIdeia));
    }

    public ResponseEntity<Object> excluirIdeia(Long idIdeia) {
        Optional<Ideia> IdeiaOptional = ideiaRepository.findById(idIdeia);

        if (IdeiaOptional.isPresent()) {
            ideiaRepository.delete(IdeiaOptional.get());
            return CustomSuccess.buildResponseEntity(MensagemConstantes.IDEIA_EXCLUIDA_COM_SUCESSO);
        } else {
            throw new CustomException.IdeiaNaoEncontradaComIdException(idIdeia);
        }
    }

    public List<IdeiaResponseDTO> listarIdeiasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
        if (idUsuario == null) {
            throw new CustomException.UsuarioNaoEncontradoException();
        }

        List<Ideia> Ideias;

        if (ano != null && mes != null) {
            LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1);
            LocalDate ultimoDiaDoMes = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());

            Date dataInicio = Date.from(primeiroDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dataFim = Date.from(ultimoDiaDoMes.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Ideias = ideiaRepository.findByUsuarioIdAndDataPrazoBetween(idUsuario, dataInicio, dataFim);
        } else {
            Ideias = ideiaRepository.findByUsuarioId(idUsuario);
        }

        return Ideias.stream()
                .map(this::converterParaIdeiaResponseDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void salvarIdeia(Ideia Ideia) {
        ideiaRepository.save(Ideia);
    }

    public Optional<CategoriaIdeia> getCategoriaPorId(Long id) {
        return categoriaIdeiaRepository.findById(id);
    }

    private IdeiaResponseDTO converterParaIdeiaResponseDTO(Ideia Ideia) {
        if (Ideia == null || Ideia.getCategoria() == null) {
            return null;
        }

        return new IdeiaResponseDTO(
                Ideia.getId(),
                Ideia.getCategoria().getId(),
                Ideia.getTitulo(),
                Ideia.getDescricao(),
                Ideia.getDataPrazo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                Ideia.isFinalizado()
        );
    }
}
