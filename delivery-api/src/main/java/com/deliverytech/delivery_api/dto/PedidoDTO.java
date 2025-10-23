package com.deliverytech.delivery_api.dto;

//import java.math.BigDecimal;
//import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

//import com.deliverytech.delivery_api.model.StatusPedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoDTO {
    //private Long id;

    @Schema(description = "Id do cliente", example = "15", required = true)
    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId; // Referencia apenas o ID do cliente

    @Schema(description = "Id do restaurante", example = "7", required = true)
    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @Schema(description = "Endereço completo para entrega do pedido", example = "Rua das Flores, 123 - Centro",  required = true)
    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoEntrega;

    @Schema(description = "Lista de itens do pedido", required = true)
    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    /* 
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String observacoes;
    private RestauranteDTO restaurante;
    */
}
