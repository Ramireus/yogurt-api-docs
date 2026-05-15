# 📋 Guía de Instalación y Desarrollo

## Requisitos Previos

- **Java 17 o superior**
- **Maven 3.6+**
- **Git**
- Base de datos (H2, MySQL, PostgreSQL, etc.)

## 🔧 Instalación Local

### 1. Clonar el Repositorio

```bash
git clone https://github.com/Ramireus/yogurt-api-docs.git
cd yogurt-api-docs/demo
```

### 2. Configurar la Base de Datos

Editar `src/main/resources/application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# Servidor
server.port=8080
```

### 3. Compilar el Proyecto

```bash
mvn clean install
```

### 4. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🧪 Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests de un módulo específico
mvn test -Dtest=DemoApplicationTests

# Con cobertura
mvn test jacoco:report
```

## 📦 Estructura del Proyecto

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── service/          # Lógica de negocio
│   │   │   ├── repository/       # Acceso a datos (JPA)
│   │   │   ├── model/            # Entidades JPA
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Excepciones personalizadas
│   │   │   └── config/           # Configuraciones
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/demo/
├── pom.xml
└── README.md
```

## 🚀 Endpoints Disponibles

### RecipeController
- `GET /api/recipes` - Listar todas
- `POST /api/recipes` - Crear
- `GET /api/recipes/{id}` - Obtener
- `PUT /api/recipes/{id}` - Actualizar
- `DELETE /api/recipes/{id}` - Eliminar

### YogurtBatchController
- `GET /api/batches` - Listar lotes
- `POST /api/batches` - Crear lote
- `GET /api/batches/{id}` - Obtener lote
- `PUT /api/batches/{id}` - Actualizar lote

### TemperatureLogRepository
- `GET /api/temperature-records` - Listar registros
- `POST /api/temperature-records` - Crear registro

### MonitoringController
- `GET /api/monitoring` - Estado general del sistema

## 📖 Swagger/OpenAPI

Una vez que la aplicación esté ejecutándose, accede a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔍 Debugging

### Modo Debug en VS Code

Agregar en `.vscode/launch.json`:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Spring Boot App",
      "request": "launch",
      "mainClass": "com.example.demo.DemoApplication",
      "projectName": "demo",
      "cwd": "${workspaceFolder}/demo",
      "console": "integratedTerminal",
      "internalConsoleOptions": "neverOpen"
    }
  ]
}
```

### Logs

Por defecto, los logs se muestran en la consola. Para cambiar el nivel:

```properties
logging.level.com.example.demo=DEBUG
logging.level.org.springframework=INFO
```

## 📝 Compilación y Build

### Crear un JAR ejecutable

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Crear imagen Docker (opcional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

## 🔄 Git Workflow

### Crear una rama para features

```bash
git checkout -b feature/nueva-funcionalidad
# Hacer cambios
git add .
git commit -m "feat: agregar nueva funcionalidad"
git push origin feature/nueva-funcionalidad
```

### Mergear a main/master

```bash
git checkout master
git pull origin master
git merge feature/nueva-funcionalidad
git push origin master
```

## 🐛 Troubleshooting

### Error: "Cannot resolve symbol 'DemoApplication'"
```bash
mvn clean install
```

### Error: "Port 8080 already in use"
```bash
# Cambiar puerto en application.properties
server.port=8081
```

### Error: "Cannot connect to database"
- Verifica las credenciales en `application.properties`
- Asegúrate de que la BD esté corriendo

## 📞 Soporte

Si encuentras problemas:
1. Revisa los logs de la aplicación
2. Verifica la configuración en `application.properties`
3. Consulta la documentación de Spring Boot

---

**Última actualización**: Mayo 2026
