package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "IngredientDto", description = "Objeto de transferencia de datos para ingredientes de una receta")
public class IngredientDto {
    @Schema(description = "Nombre del ingrediente", example = "Leche entera")
    private String name;
    
    @Schema(description = "Cantidad requerida del ingrediente", example = "1.0")
    private Double quantity;
    
    @Schema(description = "Unidad de medida del ingrediente", example = "litro")
    private String unit;
    
    @Schema(description = "Notas o instrucciones especiales para el ingrediente", example = "A temperatura ambiente")
    private String notes;
    
    @Schema(description = "Indica si este ingrediente es opcional", example = "false")
    private Boolean options;
}




