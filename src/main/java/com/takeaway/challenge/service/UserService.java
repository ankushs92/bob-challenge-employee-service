package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.auth.User;

import java.util.Optional;

public interface UserService {

    Optional<User> loadUserByUsername(String username);

}
