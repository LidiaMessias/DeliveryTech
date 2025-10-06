package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // Cadastrar novo restaurante
    public Restaurante cadastrar(Restaurante restaurante) {
        // Validar nome único
        if (restauranteRepository.findByNome(restaurante.getNome()).isPresent()) {
            throw new IllegalArgumentException("Restaurante já cadastrado: " + restaurante.getNome());
        }

        // validarDadosRestaurante(restaurante);
        validarDadosRestaurante(restaurante);
        restaurante.setAtivo(true);

        return restauranteRepository.save(restaurante);
    }

    // Buscar restaurante por ID
    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    // Listar todos os restaurantes ativos
    @Transactional(readOnly = true)
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    // Buscar por categoria
    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaAndAtivoTrue(categoria);
    }

    // Atualizar dados do restaurante
    public Restaurante atualizar(Long id, Restaurante restauranteAtualizado) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + id));

        // Verificar se nome não está sendo usado por outro restaurante
        if (!restaurante.getNome().equals(restauranteAtualizado.getNome()) &&
                restauranteRepository.findByNome(restauranteAtualizado.getNome()).isPresent()) {
            throw new IllegalArgumentException("Nome já cadastrado: " +
                    restauranteAtualizado.getNome());
        }

        // Atualizar campos
        restaurante.setNome(restauranteAtualizado.getNome());
        restaurante.setCategoria(restauranteAtualizado.getCategoria());
        restaurante.setEndereco(restauranteAtualizado.getEndereco());
        restaurante.setTelefone(restauranteAtualizado.getTelefone());
        restaurante.setTaxaEntrega(restauranteAtualizado.getTaxaEntrega());

        return restauranteRepository.save(restaurante);
    }

    // Inativar restaurante
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + id));
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);
    }

    // VALIDAÇÕES DE NEGÓCIO
    private void validarDadosRestaurante(Restaurante restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (restaurante.getTaxaEntrega() != null &&
            restaurante.getTaxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser nega􀆟va");
        }
    }
                
}
