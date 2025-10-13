package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private boolean disponivel;
}
