package fan.company.springbootkeycloakintegration.controllers;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RestTemplate restTemplate;
    private final Keycloak keycloak;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @GetMapping("/createRoleForRealm")
    public Object createRoleForRealm(@RequestParam String roleName) {

        RealmResource realm = keycloak.realm("levelup");

        // RoleRepresentation yaratish
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription("Realm role: " + roleName);

        //  Realmga role qo‘shish
        realm.roles().create(role);

        return "Realm role created successfully: " + roleName;
    }


    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @GetMapping("/createRoleForClient")
    public Object createRoleForClient(@RequestParam String roleName) {

        RealmResource realm = keycloak.realm("levelup");

        //  Clientni topish
        var client = realm.clients().findByClientId(clientId).getFirst();

        //  RoleRepresentation yaratish
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setDescription("Client role: " + roleName);

        //  Clientga role qo‘shish
        realm.clients()
                .get(client.getId())
                .roles()
                .create(role);

        return "Client role created successfully: " + roleName;
    }



    @PreAuthorize("hasRole('CLIENT_ADMIN_LEVEL_UP')")
    @GetMapping("/getAllRolesFromClient")
    public List<RoleRepresentation> getAllRolesFromClient() {
        RealmResource realm = keycloak.realm("levelup");
        var client = realm.clients().findByClientId(clientId).getFirst();
        return realm.clients()
                .get(client.getId())
                .roles()
                .list();
    }


    @PreAuthorize("hasRole('CLIENT_ADMIN_LEVEL_UP')")
    @GetMapping("/getAllRolesFromRealm")
    public List<RoleRepresentation> getAllRolesFromRealm() {
        RealmResource realm = keycloak.realm("levelup");
        return realm.roles().list();
    }


    @PreAuthorize("hasRole('REALM_ADMIN_LEVEL_UP')")
    @GetMapping("/getAllRoles")
    public List<RoleRepresentation> getAllRoles() {
        RealmResource realm = keycloak.realm("levelup");
        // Realm roles
        List<RoleRepresentation> realmRoles = realm.roles().list();
        var client = realm.clients().findByClientId(clientId).getFirst();

        List<RoleRepresentation> clientRoles = realm.clients()
                .get(client.getId())
                .roles()
                .list();
        realmRoles.addAll(clientRoles);
        return realmRoles;
    }
}