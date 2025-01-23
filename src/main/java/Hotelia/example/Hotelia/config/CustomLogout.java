package Hotelia.example.Hotelia.config;

import Hotelia.example.Hotelia.model.Token;
import Hotelia.example.Hotelia.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class CustomLogout implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public CustomLogout(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);

        if (storedToken != null){
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
