package com.takeaway.challenge.service;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.auth.Role;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserServiceTest extends ChallengeApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser_shouldReturnUserWhenQueriedByEmail() {
        //Inserting a user into database. Will only be done once.
        var insertUser = new User();
        var bcrypt = new BCryptPasswordEncoder();
        insertUser.setAccountNonExpired(true);
        insertUser.setAccountNonLocked(true);
        insertUser.setCredentialsNonExpired(true);
        insertUser.setEmail("root");
        insertUser.setPassword(bcrypt.encode("root"));
        insertUser.setEnabled(true);
        insertUser.setRole(Role.USER);
        insertUser.setCreated(ZonedDateTime.now(ZoneId.of("UTC")));
        userRepository.save(insertUser);

        //We only compare a few properties here
        var user =  userService.loadUserByUsername(insertUser.getEmail());
        assertThat(user.isPresent(), equalTo(true));
        assertThat(user.get().getEmail(), equalTo(insertUser.getEmail()));
        assertThat(user.get().getEnabled(), equalTo(insertUser.isEnabled()));
        assertThat(user.get().getRole(), equalTo(insertUser.getRole()));
    }
}
