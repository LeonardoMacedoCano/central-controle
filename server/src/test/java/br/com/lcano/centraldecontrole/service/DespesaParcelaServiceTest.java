package br.com.lcano.centraldecontrole.service;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.dto.DespesaParcelaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DespesaParcelaServiceTest {
    @InjectMocks
    private DespesaParcelaService despesaParcelaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarParcela() {
        DespesaParcelaDTO parcelaDTO = new DespesaParcelaDTO();
        parcelaDTO.setNumero(1);
        parcelaDTO.setDataVencimento(new Date());
        parcelaDTO.setValor(100.0);
        parcelaDTO.setPago(false);

        Despesa despesa = new Despesa();

        DespesaParcela novaParcela = despesaParcelaService.criarParcela(parcelaDTO, despesa);

        assertEquals(parcelaDTO.getNumero(), novaParcela.getNumero());
        assertEquals(parcelaDTO.getDataVencimento(), novaParcela.getDataVencimento());
        assertEquals(parcelaDTO.getValor(), novaParcela.getValor());
        assertEquals(parcelaDTO.getPago(), novaParcela.isPago());
    }

    @Test
    public void testCriarParcelas() {
        Despesa despesa = new Despesa();

        List<DespesaParcelaDTO> parcelasDTO = new ArrayList<>();
        DespesaParcelaDTO parcelaDTO1 = new DespesaParcelaDTO();
        parcelaDTO1.setNumero(1);
        parcelaDTO1.setDataVencimento(new Date());
        parcelaDTO1.setValor(100.0);
        parcelaDTO1.setPago(false);
        parcelasDTO.add(parcelaDTO1);

        DespesaParcelaDTO parcelaDTO2 = new DespesaParcelaDTO();
        parcelaDTO2.setNumero(2);
        parcelaDTO2.setDataVencimento(new Date());
        parcelaDTO2.setValor(150.0);
        parcelaDTO2.setPago(false);
        parcelasDTO.add(parcelaDTO2);

        List<DespesaParcela> parcelas = despesaParcelaService.criarParcelas(despesa, parcelasDTO);

        assertEquals(parcelasDTO.size(), parcelas.size());
        assertEquals(parcelaDTO1.getNumero(), parcelas.get(0).getNumero());
        assertEquals(parcelaDTO1.getDataVencimento(), parcelas.get(0).getDataVencimento());
        assertEquals(parcelaDTO1.getValor(), parcelas.get(0).getValor());
        assertEquals(parcelaDTO1.getPago(), parcelas.get(0).isPago());

        assertEquals(parcelaDTO2.getNumero(), parcelas.get(1).getNumero());
        assertEquals(parcelaDTO2.getDataVencimento(), parcelas.get(1).getDataVencimento());
        assertEquals(parcelaDTO2.getValor(), parcelas.get(1).getValor());
        assertEquals(parcelaDTO2.getPago(), parcelas.get(1).isPago());
    }
}
