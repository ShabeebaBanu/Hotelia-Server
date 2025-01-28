package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.AuthResponse;
import Hotelia.example.Hotelia.model.User;
import Hotelia.example.Hotelia.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
                    null, null);
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        Long userId = user.getId();

        return new AuthResponse(token,userId);
    }


    public AuthResponse login(User userRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getId());
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

    @Transactional
    public User updateUser(Long userId, User userRequest) {

        Optional<User> isExist = userRepository.findById(userId);

        if (isExist.isPresent()) {
            User existingUser = isExist.get();

            if (userRequest.getName() != null && !userRequest.getName().isEmpty()) {
                existingUser.setName(userRequest.getName());
            }
            if (userRequest.getPhone() != null && !userRequest.getPhone().isEmpty()) {
                existingUser.setPhone(userRequest.getPhone());
            }
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
