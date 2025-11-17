package com.utn.frc.backend.api_gw_logistica_contenedores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad reactiva para el Gateway.
     * Define qué rutas son públicas y habilita el Servidor de Recursos OAuth2.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                // Deshabilita CSRF (ya que es una API REST sin manejo de sesión/cookies)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Define las reglas de autorización para las peticiones entrantes
                .authorizeExchange(exchanges -> exchanges

                        // 1. Rutas públicas de la API
                        .pathMatchers("/solicitudes-app/publico/**").permitAll()
                        .pathMatchers("/rutas-app/publico/**").permitAll()

                        // 2. Rutas de la documentación (OpenAPI JSON/YAML)
                        .pathMatchers("/solicitudes-app/v3/api-docs/**").permitAll()
                        .pathMatchers("/rutas-app/v3/api-docs/**").permitAll()

                        // 3. Rutas del SWAGGER UI (HTML)
                        .pathMatchers("/solicitudes-app/swagger-ui.html").permitAll()
                        .pathMatchers("/rutas-app/swagger-ui.html").permitAll()

                        // 4. RUTA CRÍTICA AGREGADA: Archivos estáticos de Swagger UI (CSS, JS)
                        // Estos archivos se sirven típicamente desde el contexto /webjars/ en Spring.
                        .pathMatchers("/solicitudes-app/webjars/**").permitAll()
                        .pathMatchers("/rutas-app/webjars/**").permitAll()

                        // Permite la subruta de la interfaz de usuario de Swagger
                        .pathMatchers("/solicitudes-app/swagger-ui/**").permitAll()
                        .pathMatchers("/rutas-app/swagger-ui/**").permitAll()

                        // 5. Todas las demás peticiones deben estar autenticadas (token JWT válido)
                        .anyExchange().authenticated()
                )

                // Habilita la configuración del Servidor de Recursos OAuth2
                // para que el Gateway valide el JWT contra el issuer-uri.
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}