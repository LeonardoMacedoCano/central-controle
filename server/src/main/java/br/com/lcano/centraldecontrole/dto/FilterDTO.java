package br.com.lcano.centraldecontrole.dto;

import lombok.Data;

@Data
public class FilterDTO {
    private String field;
    private String operator;
    private String value;
}
