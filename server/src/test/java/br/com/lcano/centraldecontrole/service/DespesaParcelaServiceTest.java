package br.com.lcano.centraldecontrole.service;
import br.com.lcano.centraldecontrole.domain.CategoriaDespesa;
import br.com.lcano.centraldecontrole.domain.Despesa;
import br.com.lcano.centraldecontrole.domain.DespesaParcela;
import br.com.lcano.centraldecontrole.domain.Usuario;
import br.com.lcano.centraldecontrole.dto.DespesaParcelaDTO;
import br.com.lcano.centraldecontrole.repository.DespesaParcelaRepository;
import br.com.lcano.centraldecontrole.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DespesaParcelaServiceTest {
    @Mock
    private DespesaParcelaRepository despesaParcelaRepository;
    @InjectMocks
    private DespesaParcelaService despesaParcelaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        despesaParcelaService = new DespesaParcelaService(despesaParcelaRepository);
    }

    @Test
    void testSalvarParcelas() {
        List<DespesaParcela> parcelas = Arrays.asList(new DespesaParcela(), new DespesaParcela(), new DespesaParcela());
        despesaParcelaService.salvarParcelas(parcelas);
        verify(despesaParcelaRepository, times(1)).saveAll(any());
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

    @Test
    void testListarParcelasPorVencimento() {
        int ano = 2024;
        int mes = 3;

        Date dataInicio = DateUtil.toDate(DateUtil.getPrimeiroDiaDoMes(ano, mes));
        Date dataFim = DateUtil.toDate(DateUtil.getUltimoDiaDoMes(ano, mes));

        Usuario usuario = new Usuario("teste", "123", new Date());
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa("teste");

        Despesa despesa = new Despesa(1L, usuario, categoriaDespesa, "teste1", new Date(), new ArrayList<>());

        List<DespesaParcela> parcelasMock = Arrays.asList(
                new DespesaParcela(1L, 1, dataInicio, 10.00, false, despesa),
                new DespesaParcela(2L, 2, dataInicio, 10.00, false, despesa),
                new DespesaParcela(3L, 3, dataFim, 10.00, false, despesa));

        despesa.setParcelas(parcelasMock);

        when(despesaParcelaRepository.findByDespesaAndDataVencimentoBetween(despesa, dataInicio, dataFim))
                .thenReturn(parcelasMock);

        List<DespesaParcela> parcelas = despesaParcelaService.listarParcelasPorVencimento(despesa, ano, mes);

        assertEquals(parcelasMock.size(), parcelas.size());
        assertEquals(parcelasMock, parcelas);
    }
}
