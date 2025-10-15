package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteResponseDTO {

    private Long id;
    private String nome;
    private String categoria;
    private String endereco;
    private String telefone;
    private BigDecimal taxaEntrega;
    private boolean ativo;
    private BigDecimal avaliacao;
    
}
