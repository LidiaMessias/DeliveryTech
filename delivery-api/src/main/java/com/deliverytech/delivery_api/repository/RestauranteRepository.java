package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar por ID
    Optional<Restaurante> findById(Long id);

    // Buscar por nome (para validações, por exemplo)
    Optional<Restaurante> findByNome(String nome);

    // Buscar restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar por categoria (corrigido para bater com o que o Service precisa)
    List<Restaurante> findByCategoriaAndAtivoTrue(String categoria);

    // Por taxa de entrega menor ou igual
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);
    
    // Buscar por nome contendo (case insensitive)
    List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    // Buscar por avaliação mínima
    List<Restaurante> findByAvaliacaoGreaterThanEqualAndAtivoTrue(BigDecimal avaliacao);

    // Ordenar por avaliação (descendente)
    List<Restaurante> findByAtivoTrueOrderByAvaliacaoDesc();

    // Relatório de vendas por restaurante
    @Query("SELECT r.nome as nomeRestaurante, " +
        "SUM(p.valorTotal) as totalVendas, " +
        "COUNT(p.id) as quantidadePedidos " +
        "FROM Restaurante r " +
        "LEFT JOIN Pedido p ON r.id = p.restaurante.id " +
        "GROUP BY r.id, r.nome")
    List<RelatorioVendas> relatorioVendasPorRestaurante();

    // Query customizada - restaurantes com produtos
    @Query("SELECT DISTINCT r FROM Restaurante r JOIN r.produtos p WHERE r.ativo = true")
    List<Restaurante> findRestaurantesComProdutos();

    // Buscar por faixa de taxa de entrega
    @Query("SELECT r FROM Restaurante r WHERE r.taxaEntrega BETWEEN :min AND :max AND r.ativo = true")
    List<Restaurante> findByTaxaEntregaBetween(@Param("min") BigDecimal min,
    @Param("max") BigDecimal max);

    // Categorias disponíveis
    @Query("SELECT DISTINCT r.categoria FROM Restaurante r WHERE r.ativo = true ORDER BY r.categoria")
    List<String> findCategoriasDisponiveis();

}
