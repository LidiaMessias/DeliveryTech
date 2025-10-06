package com.deliverytech.delivery_api.model;

public enum StatusPedido {
    PENDENTE,          // O pedido foi criado pelo cliente, mas ainda não foi confirmado pelo restaurante.
    CONFIRMADO,      // O restaurante aceitou o pedido.
    PREPARANDO,      // O pedido está sendo preparado.
    SAIU_PARA_ENTREGA, // O pedido está a caminho do cliente.
    ENTREGUE,        // O pedido foi entregue com sucesso.
    CANCELADO        // O pedido foi cancelado.
}

