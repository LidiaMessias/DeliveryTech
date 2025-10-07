package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido") // É uma boa prática definir o nome da tabela
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;
    private String enderecoEntrega;
    private BigDecimal subtotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * private Long id;
     * 
     * private String enderecoEntrega;
     * private BigDecimal subTotal;
     * private BigDecimal taxaEntrega;
     * 
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name = "cliente_id", nullable = false) // Garante que todo pedido
     * tenha um cliente
     * private Cliente cliente;
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name = "restaurante_id", nullable = false) // Garante que todo
     * pedido tenha um restaurante
     * private Restaurante restaurante;
     * 
     * @Builder.Default
     * private BigDecimal valorTotal = BigDecimal.ZERO;
     * 
     * @Enumerated(EnumType.STRING)
     * private StatusPedido status;
     * 
     * @Builder.Default
     * private LocalDateTime dataPedido = LocalDateTime.now();
     * 
     * // Inicializando a lista para evitar NullPointerException
     * 
     * @Builder.Default
     * 
     * @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval =
     * true)
     * private List<ItemPedido> itens = new ArrayList<>();
     */

}
