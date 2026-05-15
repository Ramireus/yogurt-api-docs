# Manual de Usuario - Yogurt Batch Management API

## 📖 Introducción

Este manual proporciona instrucciones detalladas sobre cómo utilizar la API REST de Gestión de Lotes de Yogurt.

## 🔐 Autenticación

Actualmente, la API no requiere autenticación. Todos los endpoints son públicos.

## 📝 Estructura de Datos

### Entidad: Recipe (Receta)

```json
{
  "id": 1,
  "name": "Yogurt Natural",
  "description": "Receta de yogurt natural sin aditivos",
  "fermentationTemp": 42,
  "fermentationHours": 8,
  "ingredients": [
    {
      "id": 1,
      "name": "Leche",
      "quantity": 1000,
      "unit": "ml"
    }
  ]
}
```

### Entidad: YogurtBatch (Lote de Yogurt)

```json
{
  "id": 1,
  "batchCode": "BATCH-001",
  "recipeId": 1,
  "quantity": 100,
  "startDate": "2026-05-15",
  "status": "FERMENTATION"
}
```

### Entidad: TemperatureLog (Registro de Temperatura)

```json
{
  "id": 1,
  "batchId": 1,
  "temperature": 42.5,
  "timestamp": "2026-05-15T10:30:00",
  "notes": "Temperatura estable"
}
```

## 🚀 Guía de Uso

### 1. Obtener todas las recetas
```bash
GET /api/recipes
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "name": "Yogurt Natural",
    "description": "Receta de yogurt natural sin aditivos",
    "fermentationTemp": 42,
    "fermentationHours": 8
  }
]
```

### 2. Crear una nueva receta
```bash
POST /api/recipes
Content-Type: application/json

{
  "name": "Yogurt Fresa",
  "description": "Yogurt con sabor a fresa",
  "fermentationTemp": 42,
  "fermentationHours": 8,
  "ingredients": [
    {
      "name": "Leche",
      "quantity": 1000,
      "unit": "ml"
    },
    {
      "name": "Fresa",
      "quantity": 100,
      "unit": "g"
    }
  ]
}
```

### 3. Crear un lote de yogurt
```bash
POST /api/batches
Content-Type: application/json

{
  "batchCode": "BATCH-002",
  "recipeId": 1,
  "quantity": 50
}
```

### 4. Obtener lotes activos
```bash
GET /api/batches
```

### 5. Registrar temperatura
```bash
POST /api/temperature-records
Content-Type: application/json

{
  "batchId": 1,
  "temperature": 42.5,
  "notes": "Temperatura dentro de rango"
}
```

### 6. Obtener monitoreo actual
```bash
GET /api/monitoring
```

**Respuesta:**
```json
{
  "activeBatches": 5,
  "averageTemp": 42.3,
  "status": "OPERATING_NORMALLY"
}
```

## 🔧 Códigos de Estado HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 400 | Bad Request - Error en los datos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

## ⚠️ Manejo de Errores

Todos los errores devuelven un formato consistente:

```json
{
  "message": "Descripción del error",
  "code": "ERROR_CODE",
  "timestamp": "2026-05-15T10:30:00"
}
```

## 📊 Ejemplos Prácticos

### Caso 1: Crear una receta y un lote

1. Crear la receta:
```bash
POST /api/recipes
{
  "name": "Yogurt Griego",
  "fermentationTemp": 45,
  "fermentationHours": 6
}
```

2. Obtener el ID de la receta creada (supongamos que es 5)

3. Crear el lote con esa receta:
```bash
POST /api/batches
{
  "batchCode": "GREEK-001",
  "recipeId": 5,
  "quantity": 200
}
```

### Caso 2: Monitorear temperatura durante la fermentación

1. Cada 30 minutos, registrar la temperatura:
```bash
POST /api/temperature-records
{
  "batchId": 1,
  "temperature": 45.2,
  "notes": "Temperatura estable"
}
```

2. Consultar el estado de monitoreo:
```bash
GET /api/monitoring
```

## 🐛 Solución de Problemas

### Problema: "Recipe not found"
- **Causa**: El ID de la receta no existe
- **Solución**: Verifica que la receta exista con `GET /api/recipes`

### Problema: "Temperature out of range"
- **Causa**: La temperatura registrada no está dentro del rango permitido
- **Solución**: Verifica la temperatura de fermentación de la receta

### Problema: "Invalid batch status"
- **Causa**: El lote no está en un estado que permite esta operación
- **Solución**: Verifica el estado actual del lote

## 📱 Herramientas Recomendadas

- **Postman**: Para probar manualmente los endpoints
- **cURL**: Para pruebas desde línea de comandos
- **Swagger UI**: Accesible en `http://localhost:8080/swagger-ui.html`

## 📚 Documentación Adicional

- **OpenAPI/Swagger**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

**Última actualización**: Mayo 2026
