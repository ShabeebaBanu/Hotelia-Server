package Hotelia.example.Hotelia.model;

public class AuthResponse {

    private String token;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public AuthResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }
}
