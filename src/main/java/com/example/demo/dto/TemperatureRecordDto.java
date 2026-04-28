package com.example.demo.dto;

import com.example.demo.model.TemperatureLog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TemperatureRecordDto", description = "Objeto de transferencia de datos para registrar una medición de temperatura")
public class TemperatureRecordDto {
    @Schema(description = "Valor de temperatura en grados Celsius", example = "43.5")
    private Double temperature;
    
    @Schema(description = "Tipo de fase en la que se registra la temperatura", example = "INCUBATION")
    private TemperatureLog.LogType type;
}
