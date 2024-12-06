package com.shermatov.dreamshops.data;

import com.shermatov.dreamshops.model.Role;
import com.shermatov.dreamshops.model.User;
import com.shermatov.dreamshops.repository.RoleRepository;
import com.shermatov.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();

    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER");
        for(int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Variant" + i);
            user.setLastName("Code" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user: " + i + " created Successfully");
        }


    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        for(int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user: " + i + " created Successfully");
        }


    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.forEach(roleName -> {
            // Check if the role already exists, if not, create a new role
            Role existingRole = roleRepository.findByName(roleName);
            if (existingRole == null) {
                Role newRole = new Role(roleName); // Create new role instance
                roleRepository.save(newRole); // Save the new role to the repository
                System.out.println("Role " + roleName + " created.");
            } else {
                System.out.println("Role " + roleName + " already exists.");
            }
        });
    }
}
