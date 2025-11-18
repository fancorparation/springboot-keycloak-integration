package fan.company.springbootkeycloakintegration.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ControllerKeyCloak {

    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @GetMapping("/realmAdmin")
    public String realmAdmin() {
        return "REALM_ADMIN";
    }

    @PreAuthorize("hasRole('CLIENT_ADMIN_LEVEL_UP')")
    @GetMapping("/clientAdmin")
    public String clientAdmin() {
        return "CLIENT_ADMIN";
    }


    @PreAuthorize("hasRole('REALM_USER_LEVEL_UP')")
    @GetMapping("/realmUser")
    public String realmUser() {
        return "REALM_USER";
    }

    @PreAuthorize("hasRole('CLIENT_USER_LEVEL_UP')")
    @GetMapping("/clientUser")
    public String clientUser() {
        return "CLIENT_USER";
    }
}
