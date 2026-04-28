package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "yogurt_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "YogurtBatch", description = "Entidad que representa un lote de yogur en producción, rastreando su estado y parámetros")
public class YogurtBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del lote", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Código único del lote (generado automáticamente)", example = "YB-1704067200000")
    private String batchCode;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(description = "Receta utilizada para este lote")
    private Recipe recipe;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado actual del lote en el proceso", example = "PREPARING")
    private BatchStatus status;
    
    @Column(nullable = false)
    @Schema(description = "Volumen de leche en el lote en litros", example = "1.0")
    private Double milkVolume;
    
    @Column(nullable = false)
    @Schema(description = "Cantidad de cultivo de inicio en cucharadas", example = "2.0")
    private Double starterAmount;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura objetivo para el lote en grados Celsius", example = "43.0")
    private Double targetTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo de incubación planificado en horas", example = "8")
    private Integer incubationTime;
    
    @Schema(description = "Momento en que se inició el lote")
    private LocalDateTime startTime;
    
    @Schema(description = "Momento en que comenzó la fase de incubación")
    private LocalDateTime incubationStartTime;
    
    @Schema(description = "Momento en que terminó la fase de incubación")
    private LocalDateTime incubationEndTime;
    
    @Schema(description = "Momento en que comenzó la refrigeración")
    private LocalDateTime refrigerationStartTime;
    
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    @Builder.Default
    @Schema(description = "Registro histórico de todas las mediciones de temperatura del lote")
    private List<TemperatureLog> temperatureLogs = new ArrayList<>();
    
    @Schema(description = "Notas adicionales sobre el lote (observaciones, incidentes, etc.)")
    private String notes;
    
    @Column(nullable = false)
    @Schema(description = "Fecha y hora de creación del lote")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha y hora de la última actualización del lote")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        batchCode = "YB-" + System.currentTimeMillis();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    @Schema(name = "BatchStatus", description = "Estados posibles de un lote de yogur")
    public enum BatchStatus {
        PREPARING, 
        HEATING, 
        COOLING, 
        INOCULATING, 
        INCUBATING, 
        REFRIGERATING, 
        COMPLETED, 
        FAILED
    }
}
