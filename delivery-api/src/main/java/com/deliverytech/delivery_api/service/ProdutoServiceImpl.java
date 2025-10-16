package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.ProdutoDTO;
import com.deliverytech.delivery_api.dto.ProdutoResponseDTO;
import com.deliverytech.delivery_api.model.Produto;
import com.deliverytech.delivery_api.model.Restaurante;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ModelMapper modelMapper;

    // cadastrar novo produto
    @Override
    public ProdutoResponseDTO cadastrarProduto(ProdutoDTO produtoDto, Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com ID: " + restauranteId));

        validarDadosProduto(produtoDto);

        // Converter DTO para entidade
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produto.setRestaurante(restaurante);
        produto.setDisponivel(true);

        Produto novoProduto = produtoRepository.save(produto);
        return modelMapper.map(novoProduto, ProdutoResponseDTO.class);

    }

    // Listar produtos por restaurante
    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId) {
        List<Produto> produtos = produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
        return produtos.stream()
            .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }

    // Buscar por ID
    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarProdutoPorId(Long id){
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        return modelMapper.map(produto, ProdutoResponseDTO.class); 
    }

    // Atualizar produto
    @Override
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoDTO produtoDto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + id));

        validarDadosProduto(produtoDto);

        produtoExistente.setNome(produtoDto.getNome());
        produtoExistente.setDescricao(produtoDto.getDescricao());
        produtoExistente.setPreco(produtoDto.getPreco());
        produtoExistente.setCategoria(produtoDto.getCategoria());
        produtoExistente.setDisponivel(produtoDto.isDisponivel());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    // Alterar disponibilidade
    @Override
    public ProdutoResponseDTO alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        produtoExistente.setDisponivel(!produtoExistente.isDisponivel());

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return modelMapper.map(produtoAtualizado, ProdutoResponseDTO.class);
    }

    // Buscar por categoria
    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaAndDisponivelTrue(categoria);
        return produtos.stream()
            .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
            .collect(Collectors.toList());
    }


    private void validarDadosProduto(ProdutoDTO produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto é obrigatória");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser um valor positivo");
        }
        if (produto.getCategoria() == null || produto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria do produto é obrigatória");
        }
    }

    /*
     // Buscar por faixa de preço
    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetweenAndDisponivelTrue(precoMin, precoMax);
    }
    */
}
