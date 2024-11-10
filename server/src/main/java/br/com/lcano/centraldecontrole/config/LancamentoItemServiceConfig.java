package br.com.lcano.centraldecontrole.config;

import br.com.lcano.centraldecontrole.dto.LancamentoItemDTO;
import br.com.lcano.centraldecontrole.enums.TipoLancamentoEnum;
import br.com.lcano.centraldecontrole.service.LancamentoItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class LancamentoItemServiceConfig {
    @Bean
    public Map<TipoLancamentoEnum, LancamentoItemService<? extends LancamentoItemDTO>> lancamentoItemServicesMap(
            List<LancamentoItemService<? extends LancamentoItemDTO>> services) {
        return services.stream()
            .collect(Collectors.toMap(
                LancamentoItemService::getTipoLancamento,
                service -> service
            ));
    }
}
