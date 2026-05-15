# Yogurt Batch Management API

Una aplicación Spring Boot para la gestión integral de lotes de yogurt, incluyendo monitoreo de temperatura, recetas y control de ingredientes.

## 📋 Descripción

Este proyecto es una API REST desarrollada con Spring Boot que permite:

- **Gestión de lotes de yogurt**: Crear, actualizar y consultar lotes de producción
- **Monitoreo de temperatura**: Registrar y controlar la temperatura durante el proceso de fermentación
- **Gestión de recetas**: Administrar recetas de yogurt con sus ingredientes y proporciones
- **Control de ingredientes**: Mantener un catálogo de ingredientes disponibles

## 🚀 Características

- API REST completamente documentada con OpenAPI/Swagger
- Base de datos persistente con JPA/Hibernate
- Validación de datos con DTOs
- Manejo centralizado de excepciones
- Monitoreo en tiempo real de temperatura

## 🛠️ Requisitos Previos

- **Java 17+**
- **Maven 3.6+**
- Base de datos (configurada en `application.properties`)

## 📦 Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/yogurt-batch-management.git
cd yogurt-batch-management
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📚 API Documentation

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva de Swagger en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/          # REST Controllers
│   │   ├── service/             # Lógica de negocio
│   │   ├── repository/          # Acceso a datos
│   │   ├── model/               # Entidades JPA
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Manejo de excepciones
│   │   └── config/              # Configuraciones
│   └── resources/
│       └── application.properties  # Configuración
└── test/
    └── java/com/example/demo/    # Tests unitarios
```

## 🔧 Configuración

Editar `src/main/resources/application.properties` para configurar:

- Puerto de la aplicación
- Conexión a base de datos
- Otros parámetros de Spring Boot

## 📝 Endpoints Principales

### Recetas
- `GET /api/recipes` - Listar todas las recetas
- `POST /api/recipes` - Crear nueva receta
- `GET /api/recipes/{id}` - Obtener receta por ID
- `PUT /api/recipes/{id}` - Actualizar receta
- `DELETE /api/recipes/{id}` - Eliminar receta

### Lotes de Yogurt
- `GET /api/batches` - Listar lotes
- `POST /api/batches` - Crear nuevo lote
- `GET /api/batches/{id}` - Obtener lote por ID
- `PUT /api/batches/{id}` - Actualizar lote

### Monitoreo de Temperatura
- `GET /api/temperature-records` - Ver registros de temperatura
- `POST /api/temperature-records` - Registrar temperatura
- `GET /api/monitoring` - Estado actual de monitoreo

## 🧪 Tests

Ejecutar los tests:
```bash
mvn test
```

## 🐛 Troubleshooting

Si encuentras problemas con la conexión a la base de datos, verifica:
1. Que la base de datos esté corriendo
2. Las credenciales en `application.properties`
3. La URL de conexión

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver archivo `LICENSE` para más detalles.

## 👤 Autor

David

## 📧 Contacto

Para preguntas o sugerencias, abre un issue en el repositorio.

---

**Última actualización**: Mayo 2026
