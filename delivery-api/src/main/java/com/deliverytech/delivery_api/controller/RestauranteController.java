package com.deliverytech.delivery_api.controller;

import java.util.List;

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

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.dto.RestauranteResponseDTO;
//import com.deliverytech.delivery_api.model.Restaurante;
//import com.deliverytech.delivery_api.service.RestauranteService;
import com.deliverytech.delivery_api.service.RestauranteServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    @Autowired
    private RestauranteServiceImpl restauranteService;

    // Cadastrar um novo restaurante
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(@Valid @RequestBody RestauranteDTO restauranteDto) {
        
        RestauranteResponseDTO restauranteSalvo = restauranteService.cadastrarRestaurante(restauranteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
        
    }

    // Buscar restaurante por ID
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(restaurante);
    }

    // Listar todos os restaurantes ativos
    @GetMapping 
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantesDisponiveis() {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesDisponiveis();
        return ResponseEntity.ok(restaurantes);
    }

    // Buscar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarRestaurantesPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarRestaurantesPorCategoria(categoria);
        return ResponseEntity.ok(restaurantes);
    }

    // Atualizar dados do restaurante
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id,   
            @Valid @RequestBody RestauranteDTO restauranteDto) {
        
        RestauranteResponseDTO restauranteAtualizado = restauranteService.atualizarRestaurante(id, restauranteDto);
        return ResponseEntity.ok(restauranteAtualizado);
        
    }

    /* 
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
    */

}
