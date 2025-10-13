package com.deliverytech.delivery_api.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;
    private LocalDateTime dataCriacao;
    private List<PedidoDTO> pedidos;
}
