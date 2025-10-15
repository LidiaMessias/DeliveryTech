package com.deliverytech.delivery_api.service;

import java.util.List;

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.dto.RestauranteResponseDTO;

public interface RestauranteService {

    RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO dto);

    RestauranteResponseDTO buscarRestaurantePorId(Long id);

    List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria);

    List<RestauranteResponseDTO> buscarRestaurantesDisponiveis(); // Ativos

    RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto);

    //RestauranteResponseDTO calcularTaxaEntrega(Long restauranteId, String cep);

}
