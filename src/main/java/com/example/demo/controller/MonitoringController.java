package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TemperatureLog;
import com.example.demo.model.YogurtBatch;
import com.example.demo.repository.TemperatureLogRepository;
import com.example.demo.repository.YogurtBatchRepository;
import com.example.demo.service.TemperatureControlService;
import com.example.demo.dto.MonitoringDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
@Tag(name = "Monitoreo y Reportes", description = "Endpoints para monitorear el estado de los lotes, registros de temperatura y obtener reportes del sistema")
public class MonitoringController {
    private final YogurtBatchRepository batchRepository;
    private final TemperatureLogRepository temperatureLogRepository;
    private final TemperatureControlService temperatureControlService;

    @GetMapping("/batches/active")
    @Operation(summary = "Obtener lotes activos", description = "Retorna todos los lotes que se encuentran en procesos activos (calentamiento, incubación, refrigeración)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de lotes activos obtenida",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<YogurtBatch>> getActiveBatches(){
        List<YogurtBatch> activeBatches = batchRepository.findByStatus(YogurtBatch.BatchStatus.INCUBATING);
        activeBatches.addAll(batchRepository.findByStatus(YogurtBatch.BatchStatus.HEATING));
        activeBatches.addAll(batchRepository.findByStatus(YogurtBatch.BatchStatus.COOLING));
        activeBatches.addAll(batchRepository.findByStatus(YogurtBatch.BatchStatus.REFRIGERATING));
        return ResponseEntity.ok(activeBatches);
    }

    @GetMapping("/batches/{batchId}/temperature")
    @Operation(summary = "Obtener resumen de temperaturas de un lote", description = "Retorna un resumen estadístico de las temperaturas: actual, máxima, mínima y promedio de incubación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen de temperaturas obtenido",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MonitoringDto.TemperatureSummary.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MonitoringDto.TemperatureSummary> getBatchTemperatureSummary(@PathVariable Long batchId){
        Double currentTemp = temperatureControlService.getCurrentTemperature(batchId);
        Double maxTemp = temperatureLogRepository.getMaxTemperatureByBatch(batchId);
        Double minTemp = temperatureLogRepository.getMinTemperatureByBatch(batchId);
        Double avgTemp = temperatureLogRepository.getAverageTemperatureByBatchAndType(
            batchId, TemperatureLog.LogType.INCUBATION);

        MonitoringDto.TemperatureSummary summary = MonitoringDto.TemperatureSummary.builder()
            .currentTemperature(currentTemp)
            .maximunTemperature(maxTemp)
            .minmumTemperature(minTemp)
            .averageTemperature(avgTemp)
            .build();

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/batches/{batchId}/temperature-logs")
    @Operation(summary = "Obtener registros de temperatura de un lote", description = "Retorna todos los registros de temperatura de un lote, opcionalmente filtrados por rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros de temperatura obtenidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemperatureLog.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "Rango de fechas inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<TemperatureLog>> getTemperatureLogs(
        @PathVariable Long batchId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end){

        if(start != null && end != null){
            return ResponseEntity.ok(temperatureLogRepository.findByBatchAndTimeRange(batchId, start, end));
        }

        YogurtBatch batch = batchRepository.findById(batchId).orElseThrow();
        return ResponseEntity.ok(temperatureLogRepository.findByBatch(batch));
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Obtener dashboard general del sistema", description = "Retorna estadísticas generales del sistema incluyendo conteo de lotes por estado, lotes activos y completados hoy")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dashboard obtenido exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MonitoringDto.Dashboard.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MonitoringDto.Dashboard> getDashboard(){
        long preparingCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.PREPARING);
        long heatingCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.HEATING);
        long coolingCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.COOLING);
        long incubatingCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.INCUBATING);
        long refrigeratingCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.REFRIGERATING);
        long completedCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.COMPLETED);
        long failedCount = batchRepository.countByStatus(YogurtBatch.BatchStatus.FAILED);

        Map<String, Long> batchCounts = new HashMap<>();
        batchCounts.put("PREPARING", preparingCount);
        batchCounts.put("HEATING", heatingCount);
        batchCounts.put("COOLING", coolingCount);
        batchCounts.put("INCUBATING", incubatingCount);
        batchCounts.put("REFRIGERATING", refrigeratingCount);
        batchCounts.put("COMPLETED", completedCount);
        batchCounts.put("FAILED", failedCount);

        MonitoringDto.Dashboard dashboard = MonitoringDto.Dashboard.builder()
            .batchCounts(batchCounts)
            .activeBatchCount(preparingCount + heatingCount + coolingCount + incubatingCount + refrigeratingCount)
            .completedToday(batchRepository.findByStatusAndDateRange(
                YogurtBatch.BatchStatus.COMPLETED,
                LocalDateTime.now().withHour(0).withMinute(0),
                LocalDateTime.now()).size())
            .build();
        
        return ResponseEntity.ok(dashboard);

    }
}
