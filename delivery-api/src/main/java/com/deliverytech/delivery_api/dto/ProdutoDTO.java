package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
//import com.deliverytech.delivery_api.model.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProdutoDTO {
    //private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @NotBlank(message = "A descrição do produto é obrigatória.")
    @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;

    @NotBlank(message = "A categoria é obrigatória.")
    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres")
    private String categoria;
   
    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    //private boolean disponivel;
}
