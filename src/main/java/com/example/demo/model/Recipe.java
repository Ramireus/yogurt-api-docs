package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Recipe", description = "Entidad que representa una receta de yogur con todos sus parámetros de fabricación")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la receta", example = "1")
    private Long id;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre único de la receta", example = "Yogur Búlgaro")
    private String name;
    
    @Schema(description = "Descripción completa de la receta y sus características")
    private String description;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Builder.Default
    @Schema(description = "Lista de ingredientes requeridos para esta receta")
    private List<Ingredient> ingredients = new ArrayList<>();
    
    @Column(nullable = false)
    @Schema(description = "Volumen de leche por defecto en litros", example = "1.0")
    private Double defaultMilkVolume;
    
    @Column(nullable = false)
    @Schema(description = "Cantidad de cultivo de inicio por defecto en cucharadas", example = "2.0")
    private Double defaultStarterAmount;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de calentamiento en grados Celsius", example = "82.5")
    private Double heatingTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Duración del calentamiento a temperatura objetivo en minutos", example = "30")
    private Integer heatingDuration;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de inoculación (enfriamiento) en grados Celsius", example = "44.0")
    private Double inoculationTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Temperatura de incubación en grados Celsius", example = "43.0")
    private Double incubationTemperature;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo mínimo de incubación en horas", example = "6")
    private Integer minIncubationTime;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo máximo de incubación en horas", example = "12")
    private Integer maxIncubationTime;
    
    @Column(nullable = false)
    @Schema(description = "Tiempo de refrigeración en horas", example = "4")
    private Integer refrigerationTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Nivel de dificultad de la receta (BEGINNER, INTERMEDIATE, ADVANCED)", example = "BEGINNER")
    private DifficultyLevel difficulty;
    
    @Schema(description = "Consejos y recomendaciones para preparar el yogur según esta receta")
    private String tips;
    
    @Column(nullable = false)
    @Schema(description = "Indica si la receta está activa y disponible para nuevos lotes", example = "true")
    private Boolean active;
    
    @Schema(name = "DifficultyLevel", description = "Niveles de dificultad disponibles para una receta")
    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
