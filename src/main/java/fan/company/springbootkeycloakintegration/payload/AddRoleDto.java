package fan.company.springbootkeycloakintegration.payload;

import lombok.Data;

@Data
public class AddRoleDto {
    private String username;
    private String roleName;
}

