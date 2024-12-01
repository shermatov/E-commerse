package com.shermatov.dreamshops.data;

import com.shermatov.dreamshops.model.User;
import com.shermatov.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();

    }




    private void createDefaultUserIfNotExists() {
        for(int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Variant" + i);
            user.setLastName("Code" + i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default vet user: " + i + " created Successfully");
        }
    }
}
