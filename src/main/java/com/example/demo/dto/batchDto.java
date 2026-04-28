package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class batchDto {
    @Data
    @Schema(name = "StarBatchRequest", description = "Solicitud para iniciar un nuevo lote de yogur")
    public static class StarBatchRequest{
        @Schema(description = "ID único de la receta a utilizar", example = "1")
        private Long recipeId;
        
        @Schema(description = "Volumen de leche personalizado en litros (opcional, si no se proporciona usa el valor por defecto)", example = "1.5")
        private Double customMilkVolume;
        
        @Schema(description = "Cantidad de cultivo de inicio personalizada en cucharadas (opcional)", example = "3.0")
        private Double customStarterAmonunt;
    }

    @Data
    @Schema(name = "FailRequest", description = "Solicitud para marcar un lote como fallido")
    public static class FailRequest{
        @Schema(description = "Motivo del fallo del lote", example = "Temperatura inconsistente durante incubación")
        private String reason;
    }
}
