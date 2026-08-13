<<<<<<< HEAD
package com.expense.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                // Keep existing Vite URL + add Docker frontend URL
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:5173",
                                "http://localhost",
                                "http://localhost:80",
                                "http://13.234.67.184",
                                "http://13.234.67.184:80"));

                // Allowed HTTP methods
                configuration.setAllowedMethods(List.of(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"));

                // Allow all headers
                configuration.setAllowedHeaders(List.of("*"));

                // Expose JWT Authorization header
                configuration.setExposedHeaders(List.of("Authorization"));

                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
=======
package com.expense.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Local + EC2 frontend URLs
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost",
                "http://localhost:80",
                "http://13.200.243.91"
        ));

        // Allowed HTTP methods
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // Expose JWT Authorization header
        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
```

>>>>>>> 13eccb466418c974e3157a6956c3e4d16e590473
