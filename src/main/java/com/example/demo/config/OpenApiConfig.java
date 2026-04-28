package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Producción de Yogur")
                .version("1.0.0")
                .description("Documentación completa de la API RESTful para la gestión integral de la producción artesanal de yogur.\n\n" +
                    "Este API permite gestionar recetas, lotes de yogur en diferentes fases de producción, " +
                    "monitorear temperaturas en tiempo real y obtener reportes detallados del sistema.\n\n" +
                    "**Funcionalidades principales:**\n" +
                    "- Gestión de recetas de yogur con parámetros de fabricación\n" +
                    "- Control de lotes a través de diferentes fases (calentamiento, inoculación, incubación, refrigeración)\n" +
                    "- Monitoreo continuo de temperaturas durante el proceso\n" +
                    "- Dashboard de estadísticas y reportes del sistema")
                .contact(new Contact()
                    .name("Equipo de Soporte Técnico")
                    .email("soporte@yogurart.com")
                    .url("https://www.yogurart.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .addServersItem(new Server()
                .url("http://localhost:8082")
                .description("Servidor de Desarrollo"))
            .addServersItem(new Server()
                .url("https://api.yogurart.com")
                .description("Servidor de Producción"));
    }
}
