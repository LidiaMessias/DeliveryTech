package com.deliverytech.delivery_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;    
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String categoria; // <-- CAMPO ADICIONADO
    private BigDecimal preco; // <-- TIPO CORRIGIDO PARA BIGDECIMAL
    private boolean disponivel; // <-- CAMPO ADICIONADO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    @JsonIgnore
    private Restaurante restaurante;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itensPedido;
}
