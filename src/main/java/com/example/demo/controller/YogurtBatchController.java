package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TemperatureRecordDto;
import com.example.demo.dto.batchDto;
import com.example.demo.model.YogurtBatch;
import com.example.demo.service.YogurtMakingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
@Tag(name = "Gestión de Lotes de Yogur", description = "Endpoints para crear y gestionar lotes de yogur a través de sus fases de producción")
public class YogurtBatchController {

    private final YogurtMakingService yogurtMakingService;

    @PostMapping
    @Operation(summary = "Iniciar nuevo lote de yogur", description = "Crea un nuevo lote de yogur basado en una receta con configuraciones personalizadas opcionales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lote creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos (recipeId no existe, volúmenes negativos, etc.)"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> startNewBatch(@RequestBody batchDto.StarBatchRequest request) {
        YogurtBatch batch = yogurtMakingService.startNewBatch(
            request.getRecipeId(), 
            request.getCustomMilkVolume(), 
            request.getCustomStarterAmonunt()
        );
        return new ResponseEntity<>(batch, HttpStatus.CREATED);
    }

    @PostMapping("/{batchId}/heating")
    @Operation(summary = "Iniciar fase de calentamiento", description = "Cambia el estado del lote a HEATING para comenzar el calentamiento de la leche")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fase de calentamiento iniciada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no está en estado válido para iniciar calentamiento"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> startHeating(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.startHeating(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/inoculating")
    @Operation(summary = "Iniciar fase de inoculación", description = "Cambia el estado del lote a INOCULATING para añadir el cultivo de inicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fase de inoculación iniciada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no está en estado válido para inoculación"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> startInoculating(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.startHeating(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/inocubation")
    @Operation(summary = "Iniciar fase de incubación", description = "Cambia el estado del lote a INCUBATING para permitir que las bacterias fermenten la leche")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fase de incubación iniciada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no está en estado válido para incubación"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> starIncubation(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.startIncubation(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/refrigeration")
    @Operation(summary = "Iniciar fase de refrigeración", description = "Cambia el estado del lote a REFRIGERATING para detener la fermentación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fase de refrigeración iniciada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no está en estado válido para refrigeración"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> startRefrigeration(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.startRefrigeration(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchId}/complete")
    @Operation(summary = "Completar lote de yogur", description = "Marca el lote como COMPLETED cuando está listo para ser distribuido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lote completado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no está en estado válido para completar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> completeBatch(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.completeBatch(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{batchid}/fail")
    @Operation(summary = "Marcar lote como fallido", description = "Marca el lote como FAILED y registra el motivo del fallo para análisis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lote marcado como fallido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "El lote no puede ser marcado como fallido en su estado actual"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> markAsFailed(@PathVariable Long batchId, @RequestBody batchDto.FailRequest request){
        YogurtBatch batch = yogurtMakingService.markAsFailed(batchId, request.getReason());
        return ResponseEntity.ok(batch);
    }

    @GetMapping
    @Operation(summary = "Obtener lotes filtrando por estado", description = "Retorna una lista de lotes, opcionalmente filtrados por su estado actual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de lotes obtenida",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<YogurtBatch>> getAllBatches(@RequestParam(required = false) YogurtBatch.BatchStatus status){
        if(status != null){
            return ResponseEntity.ok(yogurtMakingService.getBatchesByStatus(status));
        }
        return ResponseEntity.ok(yogurtMakingService.getAllBatches());
    }

    @GetMapping("/{batchId}")
    @Operation(summary = "Obtener detalles de un lote específico", description = "Retorna la información completa de un lote incluyendo su historial de temperaturas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lote encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<YogurtBatch> getBatch(@PathVariable Long batchId){
        YogurtBatch batch = yogurtMakingService.getBatch(batchId);
        return ResponseEntity.ok(batch);
    }

    @PostMapping("/{bathId}/temperature")
    @Operation(summary = "Registrar temperatura del lote", description = "Registra una medición de temperatura en el lote durante una fase específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Temperatura registrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de temperatura inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> recordTemEntity(
        @PathVariable Long batchId,
        @RequestBody TemperatureRecordDto request) {
            yogurtMakingService.recordTemperature(batchId, request.getTemperature(), request.getType());
            return ResponseEntity.ok().build();
        }
}

