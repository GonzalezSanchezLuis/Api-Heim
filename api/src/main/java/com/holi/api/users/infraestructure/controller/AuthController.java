package com.holi.api.users.infraestructure.controller;

import com.holi.api.users.application.dto.UserResponse;
import com.holi.api.users.application.service.impl.UserDetailsImpl;
import com.holi.api.users.domain.entity.User;
import com.holi.api.users.infraestructure.jwt.JwtRequest;
import com.holi.api.users.infraestructure.jwt.JwtResponse;
import com.holi.api.users.infraestructure.jwt.JwtUtils;
import com.holi.api.users.infraestructure.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("auth")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> request, HttpSession session) {
        String email = request.get("email");
        String password = request.get("password");

        // Buscar el usuario en la base de datos
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Validar la contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas");
        }

        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userRole", user.getRole());

        UserResponse userResponse = new UserResponse(user.getUserId(), user.getFullName(),
                user.getEmail(), user.getRole(), user.getPhone(), user.getDocument(), user.getPhone());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout exitoso");
    }

   /* @Autowired
    private AuthenticationManager authenticationManager;


   @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("auth")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
            // Cargar usuario por correo electrónico
            UserDetails userDetails = this.userDetailsImpl.loadUserByUsername(jwtRequest.getEmail());

            authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());

            // Generar token
            String token = this.jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (DisabledException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El usuario está deshabilitado. Por favor, contacte al administrador.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }catch (BadCredentialsException e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Tu contraseña es incorrecta. Por favor intentalo de nuevo.");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        }catch (UsernameNotFoundException e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "No se encontró un usuario con ese email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Ocurrió un error inesperado. Nuestros desarrolladores ya fueron informados.");
            return  ResponseEntity.status(500).body(errorResponse);
        }

    }



    private void authenticate(String email, String password) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return ResponseEntity.ok("Logout successful");
    } */
}
