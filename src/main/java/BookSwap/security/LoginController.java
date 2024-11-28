package BookSwap.security;

import BookSwap.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class LoginController {

    @RequestMapping("/user")
    @CrossOrigin(origins = "http://localhost:5173")
    public Principal user(Principal user){
        return user;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) {
        try {
            if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                User user = new User();
                user.setId(token.getPrincipal().getAttribute("sub"));
                user.setUsername(token.getPrincipal().getAttribute("name"));
                user.setEmail(token.getPrincipal().getAttribute("email"));
                user.setPicture(token.getPrincipal().getAttribute("picture"));

                return ResponseEntity.ok()
                        .header("Access-Control-Allow-Origin", "http://localhost:5173")
                        .header("Access-Control-Allow-Credentials", "true")
                        .body(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header("Access-Control-Allow-Origin", "http://localhost:5173")
                        .header("Access-Control-Allow-Credentials", "true")
                        .body(Map.of("error", "No authenticated user"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Access-Control-Allow-Origin", "http://localhost:5173")
                    .header("Access-Control-Allow-Credentials", "true")
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Obtener la autenticación actual
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                // Realizar el logout
                new SecurityContextLogoutHandler().logout(request, response, auth);

                // Invalidar la sesión si existe
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }

                // Limpiar el contexto de seguridad
                SecurityContextHolder.clearContext();
            }

            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Origin", "http://localhost:5173")
                    .header("Access-Control-Allow-Credentials", "true")
                    .body(Map.of("message", "Logout successful"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error during logout: " + e.getMessage()));
        }
    }

}
