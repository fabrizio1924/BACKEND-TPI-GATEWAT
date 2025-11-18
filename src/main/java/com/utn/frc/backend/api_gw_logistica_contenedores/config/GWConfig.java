package com.utn.frc.backend.api_gw_logistica_contenedores.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig
{
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${apunte-api-gw-tpi.url-microservicio-solicitudes}")
                                        String uriSoicitudes,
                                        @Value("${apunte-api-gw-tpi.url-microservicio-rutas}")
                                        String uriRutas) {
        return builder.routes()
                .route(p -> p.path("/solicitudes-app/**").uri(uriSoicitudes))
                .route(p -> p.path("/rutas-app/**").uri(uriRutas))
                .build();
    }
}

//Comentario prueba git
