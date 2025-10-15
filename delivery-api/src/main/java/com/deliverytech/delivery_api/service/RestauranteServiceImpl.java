package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.RestauranteDTO;
import com.deliverytech.delivery_api.dto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Cadastrar novo restaurante
    @Override
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO restauranteDto) {
        // Validar nome único
        if (restauranteRepository.findByNome(restauranteDto.getNome()).isPresent()) {
            throw new IllegalArgumentException("Restaurante já cadastrado: " + restauranteDto.getNome());
        }

        // validarDadosRestaurante(restaurante);
        validarDadosRestaurante(restauranteDto);

        // Converter DTO para entidade
        Restaurante restaurante = modelMapper.map(restauranteDto, Restaurante.class);
        restaurante.setAtivo(true);

        Restaurante novoRestaurante = restauranteRepository.save(restaurante);
        return modelMapper.map(novoRestaurante, RestauranteResponseDTO.class);
    }

    // Buscar restaurante por ID
    @Override
    @Transactional(readOnly = true)
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com ID: " + id));

        return modelMapper.map(restaurante, RestauranteResponseDTO.class); 
    }

    // Buscar por categoria
    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria){
        List<Restaurante> restaurantes = restauranteRepository.findByCategoriaAndAtivoTrue(categoria);

        return restaurantes.stream()
            .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
            .collect(Collectors.toList());
    }

    // Listar todos os restaurantes ativos
    @Override
    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarRestaurantesDisponiveis() {
        List<Restaurante> restaurantesAtivos = restauranteRepository.findByAtivoTrue();

        return restaurantesAtivos.stream()
            .map(restaurante -> modelMapper.map(restaurante, RestauranteResponseDTO.class))
            .collect(Collectors.toList());
    }


    // Atualizar dados do restaurante
    @Override
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto){
        Restaurante restauranteExistente = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com ID: " + id));

        // Verificar se nome não está sendo usado por outro restaurante
        if (!restauranteExistente.getNome().equals(dto.getNome()) &&
                restauranteRepository.findByNome(dto.getNome()).isPresent()) {
            throw new BusinessException("Nome já cadastrado: " +
                    dto.getNome());
        }

        // Atualizar campos
        restauranteExistente.setNome(dto.getNome());
        restauranteExistente.setCategoria(dto.getCategoria());
        restauranteExistente.setEndereco(dto.getEndereco());
        restauranteExistente.setTelefone(dto.getTelefone());
        restauranteExistente.setTaxaEntrega(dto.getTaxaEntrega());

        Restaurante restauranteAtualizado = restauranteRepository.save(restauranteExistente);
        return modelMapper.map(restauranteAtualizado, RestauranteResponseDTO.class);
    }

    // VALIDAÇÕES DE NEGÓCIO
    private void validarDadosRestaurante(RestauranteDTO restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (restaurante.getTaxaEntrega() != null &&
            restaurante.getTaxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa");
        }
    }

    /* 
    // Inativar restaurante
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + id));
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);
    }
    */

}
