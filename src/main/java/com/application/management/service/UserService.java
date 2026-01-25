package com.application.management.service;

import java.util.List;

import com.application.management.model.User;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getUserById(Long id);
}
