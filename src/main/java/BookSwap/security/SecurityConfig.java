package BookSwap.security;

import BookSwap.model.entity.User;
import BookSwap.service.IUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Autowired
    private IUser userService; // Inyectamos el servicio de usuario


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configura los orígenes permitidos
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:5173"
        ));

        // Configura los métodos permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Configura las cabeceras permitidas
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "X-Requested-With"
        ));

        // Configura las cabeceras expuestas
        configuration.setExposedHeaders(Arrays.asList(
                "Set-Cookie",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "X-XSRF-TOKEN"
        ));

        // Habilita las credenciales
        configuration.setAllowCredentials(true);

        // Tiempo de caché para las respuestas preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/oauth2/**", "/login/**").permitAll()
                            .requestMatchers("/logout").permitAll()
                            .requestMatchers("/profile").authenticated()  // Solo esto para /profile
                            .anyRequest().authenticated();
                })
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println("=== SECURITY DEBUG ===");
                            System.out.println("Request intercepted by Security");
                            System.out.println("Session ID: " + request.getSession().getId());
                            System.out.println("Cookies present: " + Arrays.toString(request.getCookies()));
                            System.out.println("Exception: " + authException.getMessage());

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Not authenticated\"}");
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(1)
                )

                .oauth2Login(oauth2login -> {
                    oauth2login
                            .loginPage("http://localhost:5173/")
                            .successHandler((request, response, authentication) -> {
                                // Este bloque se ejecuta cuando el login es exitoso
                                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

                                String userId = token.getPrincipal().getAttribute("sub");
                                String username = token.getPrincipal().getAttribute("name");
                                String email = token.getPrincipal().getAttribute("email");
                                String picture = token.getPrincipal().getAttribute("picture");

                                // Verificar si el usuario ya existe
                                User existingUser = userService.findById(userId);
                                if (existingUser == null) {
                                    User newUser = new User();
                                    newUser.setId(userId);
                                    newUser.setUsername(username);
                                    newUser.setEmail(email);
                                    newUser.setPicture(picture);
                                    userService.save(newUser);
                                } else {
                                    existingUser.setUsername(username);
                                    existingUser.setEmail(email);
                                    existingUser.setPicture(picture);
                                    userService.save(existingUser);
                                }

                                // Configurar cabeceras CORS antes de la redirección
                                response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
                                response.setHeader("Access-Control-Allow-Credentials", "true");
                                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

                                // Agregar log para depuración
                                System.out.println("Login exitoso para usuario: " + authentication.getName());
                                System.out.println("Session ID: " + request.getSession().getId());
                                System.out.println("Cookies presentes: " + Arrays.toString(request.getCookies()));

                                response.sendRedirect("http://localhost:5173/homeLogged");
                            });
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\":\"Logout successful\"}");
                            response.setHeader("Content-Type", "application/json");
                            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
                            response.setHeader("Access-Control-Allow-Credentials", "true");
                        })
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN"))
                .build();
    }


}
