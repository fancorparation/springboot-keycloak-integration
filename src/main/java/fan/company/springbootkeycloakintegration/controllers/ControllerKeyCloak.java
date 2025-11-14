package uz.pdp.levelupspringbootmonolith.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ControllerKeyCloak {

    @PreAuthorize("hasRole('MAMUR')")
    @GetMapping("/foradmin")
    public String foradmin() {
        return "Tizim administratori endpointga kirdi";
    }


    @PreAuthorize("hasRole('FOYDALANUVCHI')")
    @GetMapping("/foruser")
    public String foruser() {
        return "Tizim foydalanuvchisi endpointga kirdi";
    }
}
