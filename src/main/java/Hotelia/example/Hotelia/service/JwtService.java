package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String securityKey = "10956f5f8efd38a9b3a7356adca8cc33ecaec009480992b64410907ece643976";
    @Value("${application.security.jwt.token-expiration}")
    private Long expire;


    //Generate Token (Access/Refresh) based on Expiration Date
    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("role", user.getRole().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expire))
                .signWith(getSignKey())
                .compact();

        return token;
    }

    private SecretKey getSignKey() {
        byte[] key = Decoders.BASE64URL.decode(securityKey);
        return Keys.hmacShaKeyFor(key);
    }

    //Validate Access token based on email and expiration
    public boolean isValidAccessToken(String token, UserDetails user){
        String userEmail = extractUserEmail(token);
        return (userEmail.equals(user.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    //Extract Expiration date from token
    private Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    //Extracting Email from token
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigninKey() {
        byte[] key = Decoders.BASE64URL.decode(securityKey);
        return Keys.hmacShaKeyFor(key);

    }
}
