package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.IdeiaRequestDTO;
import br.com.lcano.centraldecontrole.dto.IdeiaResponseDTO;
import br.com.lcano.centraldecontrole.domain.CategoriaIdeia;
import br.com.lcano.centraldecontrole.domain.Ideia;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.exception.IdeiaException;
import br.com.lcano.centraldecontrole.repository.CategoriaIdeiaRepository;
import br.com.lcano.centraldecontrole.repository.IdeiaRepository;
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
public class IdeiaService {
    @Autowired
    private final IdeiaRepository ideiaRepository;
    @Autowired
    private final CategoriaIdeiaRepository categoriaIdeiaRepository;

    public IdeiaService(IdeiaRepository ideiaRepository, CategoriaIdeiaRepository categoriaIdeiaRepository) {
        this.ideiaRepository = ideiaRepository;
        this.categoriaIdeiaRepository = categoriaIdeiaRepository;
    }

    @Transactional
    public void gerarIdeia(IdeiaRequestDTO data, Usuario usuario) {
        CategoriaIdeia categoria = getCategoriaPorId(data.idCategoria())
            .orElseThrow(() -> new IdeiaException.CategoriaIdeiaNaoEncontradaById(data.idCategoria()));

        Ideia novaIdeia = new Ideia(usuario, categoria, data.titulo(), data.descricao(), data.dataPrazo(), data.finalizado());
        salvarIdeia(novaIdeia);
    }

    @Transactional
    public void editarIdeia(Long idIdeia, IdeiaRequestDTO data, Usuario usuario) {
        Ideia ideiaExistente = ideiaRepository.findById(idIdeia)
            .orElseThrow(() -> new IdeiaException.IdeiaNaoEncontradaById(idIdeia));

        CategoriaIdeia categoria = getCategoriaPorId(data.idCategoria())
            .orElseThrow(() -> new IdeiaException.CategoriaIdeiaNaoEncontradaById(data.idCategoria()));

        ideiaExistente.setUsuario(usuario);
        ideiaExistente.setCategoria(categoria);
        ideiaExistente.setTitulo(data.titulo());
        ideiaExistente.setDescricao(data.descricao());
        ideiaExistente.setDataPrazo(data.dataPrazo());
        ideiaExistente.setFinalizado(data.finalizado());

        salvarIdeia(ideiaExistente);
    }

    @Transactional
    public void excluirIdeia(Long idIdeia) {
        ideiaRepository.findById(idIdeia)
            .ifPresentOrElse(
                    ideiaRepository::delete,
                () -> {
                    throw new IdeiaException.IdeiaNaoEncontradaById(idIdeia);
                }
            );
    }

    public List<IdeiaResponseDTO> listarIdeiasDoUsuario(Long idUsuario, Integer ano, Integer mes) {
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
