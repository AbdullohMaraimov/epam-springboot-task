package crm.authservice.service;

import crm.authservice.model.dto.LoginRequest;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.model.dto.RegistrationResponse;
import crm.authservice.model.entity.AuthUser;
import crm.authservice.repository.UserRepository;
import crm.authservice.service.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public RegistrationResponse register(RegisterRequest registerDto) throws IOException {
        if (userRepository.existsAuthUserByUsername(registerDto.username())) {
            throw new BadRequestException("User exists with the username: %s".formatted(registerDto.username()));
        }

        AuthUser user = AuthUser.builder()
                .username(registerDto.username())
                .password(registerDto.password())
                .build();

        userRepository.save(user);
        return new RegistrationResponse(registerDto.username(), registerDto.password());
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginDto) {
        if (loginAttemptService.isBlocked(loginDto.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is locked for a while due to many failed attempts, please try again later");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
            AuthUser user = userRepository.findByUsername(loginDto.username())
                    .orElseThrow(() -> new EntityNotFoundException("User with username : %s not found".formatted(loginDto.username())));
            return ResponseEntity.ok(jwtService.generateToken(user));
        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(loginDto.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Wrong username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }

}
