package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
//import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RestauranteDTO {
    //private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @NotNull(message = "A taxa de entrega é obrigatória.")
    private BigDecimal taxaEntrega;


    /* 
    private boolean ativo;
    private BigDecimal avaliacao;
    private List<ProdutoDTO> produtos;
    */
}
