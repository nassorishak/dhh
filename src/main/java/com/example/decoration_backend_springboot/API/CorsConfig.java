package com.example.decoration_backend_springboot.API;//package com.example.decoration_backend_springboot.API;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://192.168.0.76:3000");  // Your phone access
//        config.addAllowedOrigin("http://localhost:3000");     // Local development
//        config.addAllowedOrigin("http://127.0.0.1:3000");     // Local development
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration  // Tells Spring this is a configuration class
public class CorsConfig {

    @Bean  // Creates a Spring bean that can be used throughout the application
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins for testing (be more restrictive in production)
        config.addAllowedOriginPattern("*");  // ⚠️ Allows ANY domain to access your API

        config.addAllowedHeader("*");         // Allows ALL HTTP headers
        config.addAllowedMethod("*");         // Allows ALL HTTP methods (GET, POST, PUT, etc.)
        config.setAllowCredentials(true);     // Allows cookies/auth credentials
        config.setMaxAge(3600L);              // Caches CORS config for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Apply to ALL endpoints

        return new CorsFilter(source);  // Creates the CORS filter
    }
}