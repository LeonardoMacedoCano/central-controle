package br.com.lcano.centraldecontrole.config;

import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.LancamentoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class LancamentoItemServiceConfig {

    @Autowired
    private List<LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServices;

    @Bean
    public Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServicesMap() {
        Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> map = new HashMap<>();
        for (LancamentoItemService<? extends LancamentoItemDTO> service : lancamentoItemServices) {
            map.put(service.getTipoLancamento(), service);
        }
        return map;
    }
}
