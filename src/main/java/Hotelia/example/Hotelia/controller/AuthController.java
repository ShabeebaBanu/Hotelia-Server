package Hotelia.example.Hotelia.controller;

import Hotelia.example.Hotelia.model.AuthResponse;
import Hotelia.example.Hotelia.model.User;
import Hotelia.example.Hotelia.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User userRequest){
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User userRequest){
        return ResponseEntity.ok(authService.login(userRequest));
    }




}
