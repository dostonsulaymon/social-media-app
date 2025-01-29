package dasturlash.uz.controller;

import dasturlash.uz.dto.JwtResponseDTO;
import dasturlash.uz.dto.LoginDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok().body(authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
        JwtResponseDTO responseDTO = authService.login(loginDTO.login(), loginDTO.password());
        return ResponseEntity.ok().body(responseDTO);

    }
}
