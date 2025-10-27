package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.deliverytech.delivery_api.model.StatusPedido;

import lombok.Data;

@Data
public class PedidoResponseDTO {

    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String enderecoEntrega;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String observacoes;
    private List<ItemPedidoDTO> itens;
    private String cep;
    private String formaPagamento;
    
}
