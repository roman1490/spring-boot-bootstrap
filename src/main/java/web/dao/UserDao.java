package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void updateUser(int id, User user);
    void deleteUser(int id);
    User getUserById(int id);
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
