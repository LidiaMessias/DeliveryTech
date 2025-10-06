package com.deliverytech.delivery_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.model.ItemPedido;
import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.model.StatusPedido;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Criar novo pedido
    public Pedido criarPedido(Long clienteId, Long restauranteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " +
                        clienteId));
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " +
                        restauranteId));
        if (!cliente.isAtivo()) {
            throw new IllegalArgumentException("Cliente ina􀆟vo não pode fazer pedidos");
        }
        if (!restaurante.isAtivo()) {
            throw new IllegalArgumentException("Restaurante não está disponível");
        }
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setStatus(StatusPedido.PENDENTE);
        return pedidoRepository.save(pedido);
    }

    // Adicionar item ao pedido
    public Pedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " +
                        pedidoId));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " +
                        produtoId));
        if (!produto.isDisponivel()) {
            throw new IllegalArgumentException("Produto não disponível: " + produto.getNome());
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quan􀆟dade deve ser maior que zero");
        }
        // Verificar se produto pertence ao mesmo restaurante do pedido
        if (!produto.getRestaurante().equals(pedido.getRestaurante())) {
            throw new IllegalArgumentException("Produto não pertence ao restaurante do pedido");
        }
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        item.calcularSubtotal();

        //pedido.adicionarItem(item);
        return pedidoRepository.save(pedido);
    }

    // Confirmar pedido
    public Pedido confirmarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " +
                        pedidoId));
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser confirmados");
        }
        if (pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    // Buscar por ID
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Listar pedidos por cliente
    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }

    // Buscar por número do pedido
    @Transactional(readOnly = true)
    public Pedido buscarPorNumero(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
    }

    // Cancelar pedido
    public Pedido cancelarPedido(Long pedidoId, String motivo) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " +
                        pedidoId));
        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new IllegalArgumentException("Pedido já entregue não pode ser cancelado");
        }
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalArgumentException("Pedido já está cancelado");
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        if (motivo != null && !motivo.trim().isEmpty()) {
            pedido.setObservacoes(pedido.getObservacoes() + " | Cancelado: " + motivo);
        }
        return pedidoRepository.save(pedido);
    }
}
