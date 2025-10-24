package com.deliverytech.delivery_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Dados para inserir um item no pedido")
public class ItemPedidoDTO {

    @Schema(description = "ID do produto", example = "21", required = true)
    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @Schema(description = "Quantidade do produto", example = "2", required = true, minimum = "1", maximum = "10")
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "Quantidade máxima é 10")
    private Integer quantidade;

}
