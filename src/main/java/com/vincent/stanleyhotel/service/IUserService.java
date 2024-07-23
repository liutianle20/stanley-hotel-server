package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.model.User;

import java.util.List;

public interface IUserService {
    User register(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
