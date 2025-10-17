package com.deliverytech.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.PedidoDTO;
import com.deliverytech.delivery_api.dto.PedidoResponseDTO;
import com.deliverytech.delivery_api.model.StatusPedido;
import com.deliverytech.delivery_api.service.PedidoServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoServiceImpl pedidoService;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoDTO dto) {
            
        PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) { 
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);       
    }

    // Listar pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    // Atualizar status do pedido 
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(@PathVariable Long id,
            @Valid @RequestBody StatusPedido novoStatus) {

        PedidoResponseDTO pedido = pedidoService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(pedido);
    }

    // Cancelar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularTotal(@Valid @RequestBody List<ItemPedidoDTO> itens) {
        BigDecimal total = pedidoService.calcularTotalPedido(itens);
        return ResponseEntity.ok(total);
    }
    
}

/*
// Adicionar item ao pedido
    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<?> adicionarItem(@PathVariable Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {
        try {
            Pedido pedido = pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Confirmar pedido
    @PutMapping("/{pedidoId}/confirmar")
    public ResponseEntity<?> confirmarPedido(@PathVariable Long pedidoId) {
        try {
            Pedido pedido = pedidoService.confirmarPedido(pedidoId);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
*/
