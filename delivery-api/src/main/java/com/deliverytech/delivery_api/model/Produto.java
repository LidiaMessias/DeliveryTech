package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;    
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String categoria; // <-- CAMPO ADICIONADO
    private BigDecimal preco; // <-- TIPO CORRIGIDO PARA BIGDECIMAL
    private boolean disponivel; // <-- CAMPO ADICIONADO
    private String imagemUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itensPedido;
}
