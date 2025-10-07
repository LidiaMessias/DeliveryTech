package com.deliverytech.delivery_api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // Cadastrar um novo restaurante
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteSalvo = restauranteService.cadastrar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Buscar restaurante por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteService.buscarPorId(id);
        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todos os restaurantes ativos
    @GetMapping 
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = restauranteService.listarAtivos();
        return ResponseEntity.ok(restaurantes);
    }

    // Buscar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Restaurante>> buscarPorCategoria(@PathVariable String categoria) {
        List<Restaurante> restaurantes = restauranteService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(restaurantes);
    }

    // Atualizar dados do restaurante
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,   
            @Valid @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAtualizado = restauranteService.atualizar(id, restaurante);
            return ResponseEntity.ok(restauranteAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    // Inativar restaurante (soft delete)
    @PutMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            restauranteService.inativar(id);
            return ResponseEntity.ok().body("Restaurante inativado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

}
