package com.deliverytech.delivery_api.dto;

import java.time.LocalDateTime;

import com.deliverytech.delivery_api.model.Usuario;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String nome;
    private String email;
    private String role;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private Long restauranteId;

    public UserResponse() {

    }   

    public UserResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.role = usuario.getRole().name();
        this.ativo = usuario.isAtivo();
        this.dataCriacao = usuario.getDataCriacao();
        this.restauranteId = usuario.getRestauranteId();
    }
}
