package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.repository.UserRepository;
import com.takeaway.challenge.service.UserService;
import com.takeaway.challenge.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    UserServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> loadUserByUsername(final String username) {
        Assert.notEmptyString(username, "username cannot be null or empty");
        return repository.findOneByEmail(username);
    }
}
