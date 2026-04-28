package com.example.demo.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Ingredient", description = "Entidad que representa un ingrediente individual en una receta")
public class Ingredient {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del ingrediente", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre del ingrediente", example = "Leche entera")
    private String name;
    
    @Schema(description = "Cantidad del ingrediente requerida", example = "1.0")
    private Double quantity;
    
    @Schema(description = "Unidad de medida", example = "litro")
    private String unit;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(description = "Receta a la que pertenece este ingrediente")
    private Recipe recipe;
    
    @Schema(description = "Notas especiales sobre el ingrediente")
    private String notes;
    
    @Column(nullable = false)
    @Schema(description = "Indica si este ingrediente es opcional en la receta", example = "false")
    private Boolean optional;
}
