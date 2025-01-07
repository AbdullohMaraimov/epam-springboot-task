package crm.authservice.controller;

import crm.authservice.model.dto.LoginRequest;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.model.dto.RegistrationResponse;
import crm.authservice.model.dto.TokenValidationRequest;
import crm.authservice.service.AuthService;
import crm.authservice.service.security.CustomUserDetailsService;
import crm.authservice.service.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegisterRequest request) throws IOException {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }


    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        authService.deleteByUsername(username);
        return ResponseEntity.ok("Trainee with username " + username + " deleted successfully");
    }

}
