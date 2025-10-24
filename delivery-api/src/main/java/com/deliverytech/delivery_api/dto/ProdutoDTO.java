package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
//import com.deliverytech.delivery_api.model.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro de produto")
public class ProdutoDTO {
    //private Long id;

    @Schema(description = "Nome do produto", example = "Pizza 4 Queijos", required = true, minLength = 3, maxLength = 50)
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Muçarela, Provolone, Parmesão e Gorgonzola com azeitonas", required = true, minLength = 3, maxLength = 100)
    @NotBlank(message = "A descrição do produto é obrigatória.")
    @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
    private String descricao;

    @Schema(description = "Preço do produto em reais", example = "58.90", minimum = "0,01")
    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Pizza", required = true)
    @NotBlank(message = "A categoria é obrigatória.")
    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres")
    private String categoria;
   
    @Schema(description = "ID do restaurante", example = "2", required = true)
    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @Schema(description = "Disponibilidade do produto", example = "true")
    private boolean disponivel;
}
