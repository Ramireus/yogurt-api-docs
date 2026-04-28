package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Recipe;
import com.example.demo.service.RecipeService;
import com.example.demo.dto.RecipeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Gestión de Recetas", description = "Endpoints para crear, actualizar y consultar recetas de yogur")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Crear nueva receta", description = "Crea una nueva receta de yogur con todos los parámetros necesarios para el proceso de elaboración")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Receta creada exitosamente", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos en la solicitud"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDto recipeDto){
        Recipe recipe = recipeService.createRecipe(recipeDto);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar receta existente", description = "Actualiza los parámetros de una receta existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada con el ID especificado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto){
        Recipe recipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener receta por ID", description = "Recupera los detalles completos de una receta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id){
        Recipe recipe = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las recetas activas", description = "Retorna un listado de todas las recetas activas disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de recetas obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        return ResponseEntity.ok(recipeService.getAllActiveRecipes());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar recetas por palabra clave", description = "Busca recetas que coincidan con la palabra clave proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Palabra clave inválida o vacía"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam String keyword){
        return ResponseEntity.ok(recipeService.searchRecipes(keyword));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar receta", description = "Desactiva una receta existente para que no sea usable en nuevos lotes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta desactivada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deactivateRecipe(@PathVariable Long id){
        recipeService.deactivateRecipe(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activar receta", description = "Activa una receta desactivada para que sea usable en nuevos lotes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta activada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> activateRecipe(@PathVariable Long id){
        recipeService.activateRecipe(id);
        return ResponseEntity.ok().build();
    }

}
