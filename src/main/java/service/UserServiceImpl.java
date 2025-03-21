package service;

import entity.User;
import entity.UserRole;
import exception.DuplicateResourceException;
import exception.InvalidDataInputException;
import exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("email already exists");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new InvalidDataInputException("password is incorrect");
        }

        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseNotFoundException("user not found"));

        if (!isValidPassword(newPassword)) {
            throw new InvalidDataInputException("PASSWORD SHOULD BE AT LEAST 8 CHARACTERS");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers(UserRole role, String firstName, String lastName, String email) {

        List<User> users = userRepository.findAll();

        if (role != null) {
            users.removeIf(user -> user.getRole() != role);
        }

        if (firstName != null && !firstName.isEmpty()) {
            users.removeIf(user -> !user.getFirstName().contains(firstName));
        }

        if (lastName != null && !lastName.isEmpty()) {
            users.removeIf(user -> !user.getLastName().contains(lastName));
        }

        if (email != null && !email.isEmpty()) {
            users.removeIf(user -> !user.getEmail().contains(email));
        }

        return users;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("USER NOT FOUND!!"));
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasDigit;
    }
}