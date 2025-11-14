package fan.company.springbootkeycloakintegration.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
