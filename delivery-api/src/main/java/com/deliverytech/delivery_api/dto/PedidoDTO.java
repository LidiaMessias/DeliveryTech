package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deliverytech.delivery_api.model.StatusPedido;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String observacoes;
    private Long clienteId; // Referencia apenas o ID do cliente
    private RestauranteDTO restaurante;
}
