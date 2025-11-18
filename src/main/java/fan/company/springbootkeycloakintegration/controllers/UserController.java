package fan.company.springbootkeycloakintegration.controllers;

import fan.company.springbootkeycloakintegration.payload.AddRoleDto;
import fan.company.springbootkeycloakintegration.payload.UserDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAction")
public class UserController {

    private final RestTemplate restTemplate;

    private final Keycloak keycloak;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @PreAuthorize("hasRole('CLIENT_ADMIN_LEVEL_UP')")
    @PostMapping("/register")
    public Object save(@RequestBody UserDto userDto) {
        RealmResource realmResource = keycloak.realm("levelup");
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getUsername());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setEnabled(true);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false); // vaqtinchalik parol emas
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDto.getPassword());
        userRepresentation.setCredentials(List.of(credential));
        Response response = realmResource.users().create(userRepresentation);
        return "Success";
    }


    @PreAuthorize("hasRole('CLIENT_ADMIN_LEVEL_UP')")
    @GetMapping("/getAllUsers")
    public List<UserRepresentation> getAllUsers() {
        RealmResource realmResource = keycloak.realm("levelup");
        return realmResource.users().list();
    }




    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @PostMapping("/addClientRoleForUser")
    public String addClientRoleForUser(@RequestBody AddRoleDto dto) {

        RealmResource realm = keycloak.realm("levelup");

        // 1. Foydalanuvchini topish
        var users = realm.users().search(dto.getUsername());
        if (users.isEmpty()) {
            return "User not found";
        }
        var user = users.get(0);

        // 2. Clientni topish
        var client = realm.clients().findByClientId(clientId).getFirst();

        // 3. Beriladigan client role
        RoleRepresentation clientRole = realm.clients()
                .get(client.getId())
                .roles()
                .get(dto.getRoleName())
                .toRepresentation();

        // 4. Role ni client-level qo‘shish
        realm.users()
                .get(user.getId())
                .roles()
                .clientLevel(client.getId())
                .add(List.of(clientRole));

        return "Client role added successfully";
    }



    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @PostMapping("/addRealmRoleForUser")
    public String addRealmRoleForUser(@RequestBody AddRoleDto dto) {

        RealmResource realm = keycloak.realm("levelup");

        // 1. Foydalanuvchini topish
        var users = realm.users().search(dto.getUsername());
        if (users.isEmpty()) {
            return "User not found";
        }
        var user = users.get(0);

        // 2. Beriladigan realm role
        RoleRepresentation realmRole = realm
                .roles()
                .get(dto.getRoleName())
                .toRepresentation();

        // 3. Role ni biriktirish
        realm.users()
                .get(user.getId())
                .roles()
                .realmLevel()
                .add(List.of(realmRole));

        return "Realm role added successfully";
    }



}
