package com.orion.DigiWallet.service;

import com.orion.DigiWallet.model.User;
import com.orion.DigiWallet.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users from database");

        List<User> users = userRepository.findAll();
        logger.info("Total users fetched: {}", users.size());
        for (User u: users){
            String greet = generateGreetingMsg(u.getRole());
            u.setUserGreetingMessage(greet);
        }
        return users;
        //TODO: 1.4
        // For each user in the list, call generateGreetingMsg(user)
        // before returning the list
        // Hint: Use a for-each loop to iterate through the users list
        // test the result on swagger or postman

    }

    public User getUserById(Long id) {

        //TODO: 1.1
        // Log incoming request with user ID
        // Example: logger.info("Fetching user with id {}", id);
        // Fetch user from repository
        // test the result on swagger or postman
        logger.info("Fetching user with id {}", id);
        User user = userRepository.findById(id).orElse(null);

//        if (user != null) {
            String greet = generateGreetingMsg(user.getRole());
            user.setUserGreetingMessage(greet);
//        }
        return user;

        //TODO: 1.3
        // Before returning the User object, call generateGreetingMsg(role)
        // role as string can be obtained from user.getRole()
        // Example: String greeting = generateGreetingMsg(user.getRole());
        // Then set this greeting message into the User object
        // Hint: Use user.setUserGreetingMessage(greeting)
        // test the result on swagger or postman

    }

    @Transactional
    public User createUser(User user) {
        logger.info("Creating new user with username {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with id {}", savedUser.getId());
        return savedUser;
    }

    String generateGreetingMsg(String role) {

        //TODO: 1.2
        // Perform a case-insensitive check to determine the role.
        // If the role is ADMIN, append an admin-specific message
        // Example: "Admin access enabled"
        // If the role is NOT ADMIN, append a standard user message
        // Example: "User access"
        // return the complete greeting message as a String
        // write a unit test to verify this method works as expected
        String op = "";
        if (role!=null && role.equalsIgnoreCase("admin")){
            op = "Admin access enabled";
        } else if (role!=null && role.equalsIgnoreCase("user")) {
            op = "User Access";
        } else {
            op = "User not available";
        }
        return op;
    }

    public User updateUserStatus(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
            user.setStatus("INACTIVE");
        } else {
            user.setStatus("ACTIVE");
        }

        User updatedUser = userRepository.save(user);
        logger.info("User status updated successfully for id {}", updatedUser.getId());

        return updatedUser;
    }

    //TODO: 1.5
    // READ ONLY
    // Simple Calculator methods add, subtract, multiply, divide
    // Go through the basic operations and implementations
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
