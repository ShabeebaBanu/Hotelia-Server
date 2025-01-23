package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.AuthResponse;
import Hotelia.example.Hotelia.model.User;
import Hotelia.example.Hotelia.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //User Registration
    public AuthResponse register(User userRequest){
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){
            return new AuthResponse(
                    null);
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }


    public AuthResponse login(User userRequst){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequst.getEmail(),
                        userRequst.getPassword()
                )
        );

        User user = userRepository.findByEmail(userRequst.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }




    public User getUserProfile(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or blank");
        }

        String userEmail;
        try {
            userEmail = jwtService.extractUserEmail(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + userEmail + " not found"));
    }

}
