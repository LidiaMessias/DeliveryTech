package com.deliverytech.delivery_api.dto;

//import java.math.BigDecimal;
//import java.time.LocalDateTime;
import java.util.List;

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

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId; // Referencia apenas o ID do cliente

    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoEntrega;

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
