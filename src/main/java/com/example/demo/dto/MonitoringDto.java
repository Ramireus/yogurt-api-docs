package com.example.demo.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class MonitoringDto {
    @Data
    @Builder
    @Schema(name = "TemperatureSummary", description = "Resumen estadístico de temperaturas de un lote")
    public static class TemperatureSummary{
        @Schema(description = "Temperatura actual del lote en grados Celsius", example = "43.5")
        private Double currentTemperature;
        
        @Schema(description = "Temperatura máxima registrada en el lote en grados Celsius", example = "44.0")
        private Double maximunTemperature;
        
        @Schema(description = "Temperatura mínima registrada en el lote en grados Celsius", example = "42.5")
        private Double minmumTemperature;
        
        @Schema(description = "Temperatura promedio durante la incubación en grados Celsius", example = "43.2")
        private Double averageTemperature;
    }

    @Data
    @Builder
    @Schema(name = "Dashboard", description = "Información agregada y estadísticas del sistema de producción de yogur")
    public static class Dashboard{
        @Schema(description = "Mapa con conteos de lotes por cada estado del proceso")
        private Map<String, Long> batchCounts;
        
        @Schema(description = "Número total de lotes activos (en proceso)", example = "5")
        private Long activeBatchCount;
        
        @Schema(description = "Número de lotes completados durante el día actual", example = "12")
        private Integer completedToday;
    }
}
