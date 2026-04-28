package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RecipeDto", description = "Objeto de transferencia de datos para crear y actualizar recetas de yogur")
public class RecipeDto {
    @Schema(description = "Nombre único de la receta", example = "Yogur Griego Casero")
    private String name;
    
    @Schema(description = "Descripción detallada del proceso y características de la receta", example = "Receta de yogur griego con leche entera y cultivo natural")
    private String description;
    
    @Schema(description = "Volumen de leche por defecto en litros", example = "1.0")
    private Double defaulMilkVolume;
    
    @Schema(description = "Cantidad de cultivo de inicio por defecto en cucharadas", example = "2.0")
    private Double defaulStarterAmount;
    
    @Schema(description = "Temperatura de calentamiento en grados Celsius", example = "82.5")
    private Double heatingTemperature;
    
    @Schema(description = "Duración del calentamiento a temperatura objetivo en minutos", example = "30")
    private Integer heatingDuration;
    
    @Schema(description = "Temperatura de inoculación (enfriamiento) en grados Celsius", example = "44.0")
    private Double innoculationTemperature;
    
    @Schema(description = "Temperatura de incubación en grados Celsius", example = "43.0")
    private Double incubationTemperature;
    
    @Schema(description = "Tiempo mínimo de incubación en horas", example = "6")
    private Integer minIncubationTime;
    
    @Schema(description = "Tiempo máximo de incubación en horas", example = "12")
    private Integer maxIncubationTime;
    
    @Schema(description = "Tiempo de refrigeración en horas", example = "4")
    private Integer refrigerationTime;
    
    @Schema(description = "Nivel de dificultad de la receta", example = "BEGINNER")
    private Recipe.DifficultyLevel difficulty;
    
    @Schema(description = "Consejos y recomendaciones para hacer la receta", example = "Usar leche fresca y evitar movimientos bruscos durante la incubación")
    private String tips;
    
    @Schema(description = "Lista de ingredientes necesarios para la receta")
    private List<IngredientDto> ingredients;
}
