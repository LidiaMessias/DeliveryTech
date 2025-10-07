package com.deliverytech.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.service.ProdutoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Cadastrar um novo produto
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Produto produto,
            @RequestParam Long restauranteId) {
        try {
            Produto produtoSalvo = produtoService.cadastrar(produto, restauranteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar produtos por restaurante
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<Produto> produtos = produtoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }

    // Buscar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
            @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Alterar disponibilidade
    @PutMapping("/{id}/disponibilidade")
    public ResponseEntity<?> alterarDisponibilidade(@PathVariable Long id,
            @RequestParam boolean disponivel) {
        try {
            produtoService.alterarDisponibilidade(id, disponivel);
            return ResponseEntity.ok().body("Disponibilidade alterada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Buscar por faixa de preço
    @GetMapping("/faixa-preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(@RequestParam("precoMin") String precoMinStr,
            @RequestParam("precoMax") String precoMaxStr) {
        try {
            // Converter os parâmetros de String para BigDecimal
            BigDecimal precoMin = new BigDecimal(precoMinStr);
            BigDecimal precoMax = new BigDecimal(precoMaxStr);

            List<Produto> produtos = produtoService.buscarPorFaixaPreco(precoMin, precoMax);
            return ResponseEntity.ok(produtos);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
