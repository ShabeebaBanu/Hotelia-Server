package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.User;
import Hotelia.example.Hotelia.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;



    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }





}
