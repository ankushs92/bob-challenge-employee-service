package com.takeaway.challenge;

import com.takeaway.challenge.domain.auth.Role;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
@EnableAsync
public class ChallengeApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Inserting a user into database. Will only be done once.
        var user = new User();
        var bcrypt = new BCryptPasswordEncoder();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("root");
        user.setPassword(bcrypt.encode("root"));
        user.setEnabled(true);
        user.setRole(Role.USER);
        user.setCreated(ZonedDateTime.now(ZoneId.of("UTC")));

        if(userRepository.findOneByEmail("root").isEmpty()) {
            userRepository.save(user);
        }
    }
}
