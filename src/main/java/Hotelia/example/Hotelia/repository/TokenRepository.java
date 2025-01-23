package Hotelia.example.Hotelia.repository;

import Hotelia.example.Hotelia.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByAccessToken(String token);
    Optional<Token> findByRefreshToken(String token);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId")
    List<Token> findAllAccessTokenByUser(Long userId);
}
