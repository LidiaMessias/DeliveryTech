package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String categoria;
    private String telefone;
    private String horarioFuncionamento;
    private BigDecimal avaliacao;

    @Builder.Default
    private BigDecimal taxaEntrega = BigDecimal.ZERO;

    private Integer tempoEntrega;

    @Builder.Default
    private boolean ativo = true;

    @Builder.Default
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();
    
}