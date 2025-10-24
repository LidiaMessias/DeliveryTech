package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
//import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro de restaurante")
public class RestauranteDTO {
    //private Long id;

    @Schema(description = "Nome do restaurante", example = "Pizza Express", required = true)
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Schema(description = "Categoria do restaurante", example = "Italiana", allowableValues = {"Italiana", "Brasileira", "Japonesa", "Mexicana", "Árabe"})
    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @Schema(description = "Endereço completo do restaurante", example = "Rua das Flores, 123 - Centro")
    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Schema(description = "Telefone para contato", example = "13912349999")
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Schema(description = "Taxa de entrega em reais", example = "5.50", minimum = "0")
    @NotNull(message = "A taxa de entrega é obrigatória.")
    private BigDecimal taxaEntrega;

    
    /* 
    @Schema(description = "Tempo estimado de entrega em minutos", example = "45", minimum = "10", maximum = "120")
    @NotNull(message = "Tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer tempoEntrega;

    @Schema(description = "Horário de funcionamento", example = "08:00-22:00")
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;

    private boolean ativo;
    private BigDecimal avaliacao;
    private List<ProdutoDTO> produtos;
    */
}
