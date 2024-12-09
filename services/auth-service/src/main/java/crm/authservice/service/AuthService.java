package crm.authservice.service;

import crm.authservice.model.dto.LoginRequest;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.model.dto.RegistrationResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    RegistrationResponse register(RegisterRequest registerDto) throws IOException;

    ResponseEntity<String> login(LoginRequest loginDto);

}
