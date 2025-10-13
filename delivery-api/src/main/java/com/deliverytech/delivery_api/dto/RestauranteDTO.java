package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class RestauranteDTO {
    private Long id;
    private String nome;
    private String categoria;
    private String endereco;
    private String telefone;
    private BigDecimal taxaEntrega;
    private BigDecimal avaliacao;
    private boolean ativo;
    private List<ProdutoDTO> produtos;
}
