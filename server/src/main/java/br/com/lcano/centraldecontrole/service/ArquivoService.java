package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.domain.Arquivo;
import br.com.lcano.centraldecontrole.exception.ArquivoException;
import br.com.lcano.centraldecontrole.repository.ArquivoRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class ArquivoService {
    @Autowired
    ArquivoRepository arquivoRepository;
    @Autowired
    DateUtil dateUtil;

    public Optional<Arquivo> findByHash(String hash) {
        return this.arquivoRepository.findByHash(hash);
    }

    public Arquivo findByIdwithValidation(Long id) {
        return arquivoRepository.findById(id)
                .orElseThrow(() -> new ArquivoException.ArquivoNaoEncontrado(id));
    }

    public Optional<Arquivo> findById(Long id) {
        return arquivoRepository.findById(id);
    }

    public String calculateHash(InputStream is) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytesBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(bytesBuffer)) != -1) {
            digest.update(bytesBuffer, 0, bytesRead);
        }
        byte[] hashBytes = digest.digest();
        return HexFormat.of().formatHex(hashBytes);
    }

    public void validateHash(String hash) {
        this.findByHash(hash)
                .ifPresent(arquivo -> {
                    throw new ArquivoException.ArquivoJaImportado(arquivo.getId());
                });
    }

    public Arquivo uploadArquivo(MultipartFile file) throws Exception {
        String hash = this.calculateHash(file.getInputStream());
        this.validateHash(hash);

        Arquivo arquivo = new Arquivo();
        arquivo.setNome(FilenameUtils.getBaseName(file.getOriginalFilename()));
        arquivo.setConteudo(file.getBytes());
        arquivo.setExtensao(FilenameUtils.getExtension(file.getOriginalFilename()));
        arquivo.setHash(hash);
        arquivo.setDataImportacao(dateUtil.getDataAtual());

        return arquivoRepository.save(arquivo);
    }

}
