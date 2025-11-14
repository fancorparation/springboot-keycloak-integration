package uz.pdp.levelupspringbootmonolith.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/data")
    @PreAuthorize("hasRole('ADMIN')")
    public String data() {
        return "Check CORS data";
    }

    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal OidcUser principal) {
        return principal;
    }
}
