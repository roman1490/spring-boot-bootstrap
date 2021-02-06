package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void updateUser(int id, User newUser);
    void deleteUser(int id);
    User getUserById(int id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
