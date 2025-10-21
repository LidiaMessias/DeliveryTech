package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.ProdutoDTO;
import com.deliverytech.delivery_api.dto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.service.ProdutoServiceImpl;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoServiceImpl produtoService;

    // Cadastrar um novo produto
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Valid @RequestBody ProdutoDTO produtoDto,
            @RequestParam Long restauranteId) {
        
        ProdutoResponseDTO produtoSalvo = produtoService.cadastrarProduto(produtoDto, restauranteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);

    }
    
    // Listar produtos por restaurante
    @GetMapping("/{restauranteId}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);    
    }
    
    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id,
            @Valid @RequestBody ProdutoDTO produtoDto) {
        
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDto);
        return ResponseEntity.ok(produtoAtualizado);  
    }

    // Alterar disponibilidade
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> alterarDisponibilidade(@PathVariable Long id,
            @RequestParam boolean disponivel) {
        
        ProdutoResponseDTO produto = produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.ok(produto);
    }
    

    // Buscar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

}

    /* 
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
    */
