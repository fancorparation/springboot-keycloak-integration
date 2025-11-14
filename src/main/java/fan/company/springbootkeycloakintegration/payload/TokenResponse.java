package fan.company.springbootkeycloakintegration.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;

    // Xatolik uchun qo‘shimcha konstruktor
    public TokenResponse(String error, String access_token, String refresh_token, int expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.token_type = error;
        this.expires_in = expires_in;
    }
}
