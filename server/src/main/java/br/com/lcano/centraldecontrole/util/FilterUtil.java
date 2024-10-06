package br.com.lcano.centraldecontrole.util;

import br.com.lcano.centraldecontrole.dto.FilterDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Function;

public class FilterUtil {
    public static <T> Specification<T> buildSpecificationsFromDTO(List<FilterDTO> filters,
                                                                  Function<FilterDTO, Specification<T>> applySpecificationFunction) {
        return (filters == null || filters.isEmpty())
                ? null
                : filters.stream()
                .map(applySpecificationFunction)
                .reduce(Specification::and)
                .orElse(null);
    }
}
