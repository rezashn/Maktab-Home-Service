package service;

import entity.User;
import entity.UserRole;

import java.util.List;

public interface UserService {
    public User registerUser(User user);

    public User changePassword(Long userId, String newPassword);

    public List<User> getUsers(UserRole role, String firstName, String lastName, String email);

    public User getUserById(long id);
}
