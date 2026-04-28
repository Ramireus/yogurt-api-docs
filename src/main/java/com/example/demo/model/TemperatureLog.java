package com.example.demo.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temperature_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "TemperatureLog", description = "Entidad que registra una medición individual de temperatura en un lote")
public class TemperatureLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del registro de temperatura", example = "1")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    @Schema(description = "Lote de yogur al que pertenece este registro")
    private YogurtBatch batch;
    
    @Column(nullable = false)
    @Schema(description = "Valor de temperatura registrado en grados Celsius", example = "43.5")
    private Double temperature;
    
    @Column(nullable = false)
    @Schema(description = "Momento en que se realizó la medición")
    private LocalDateTime recordedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo de fase en la que se realizó la medición", example = "INCUBATION")
    private LogType type;
    
    @Schema(description = "Notas adicionales sobre la medición")
    private String notes;
    
    @Schema(name = "LogType", description = "Tipos de fases en las que se puede registrar temperatura")
    public enum LogType {
        HEATING, COOLING, INCUBATION, REFRIGERATION, MANUAL
    }
}
