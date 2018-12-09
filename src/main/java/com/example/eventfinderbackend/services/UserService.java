package com.example.eventfinderbackend.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.eventfinderbackend.models.User;
import com.example.eventfinderbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*" , allowCredentials = "true" , allowedHeaders = "*")
public class UserService {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/user")
    public ResponseEntity findAllUsers() {
        try {
            List<User> userList = (List<User>) userRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userList);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/api/user/{id}")
    public User findUserById(@PathVariable("id") int userId) {
        return userRepository.findById(userId).get();
    }

    @PostMapping("/api/user")
    public List<User> createUser(@RequestBody User user) {
        userRepository.save(user);
        return (List<User>)userRepository.findAll();
    }


    @PostMapping("/api/user/register")
    public User register(
            @RequestBody User user,
            HttpSession session) {
        session.setAttribute("currentUser", user);
        userRepository.save(user);
        return user;
    }


    @GetMapping("/api/user/profile")
    public User profile(HttpSession session) {
        User currentUser = (User)session.getAttribute("currentUser");
        return currentUser;
    }

    @PostMapping("/api/user/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @PostMapping("/api/user/login")
    public ResponseEntity login(
            @RequestBody User credentials,
            HttpSession session) {
        try {
            User user = userRepository.findUserByCredentials(credentials.getUsername(), credentials.getPassword());
            if (user==null) {
                throw new Exception("Incorrect credentials! Please try again!");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @PutMapping("/api/user/{id}")
    public List<User> updateUser
            (@PathVariable("id") int userId,@RequestBody User user) {
        User u = userRepository.findById(userId).get();
        u.setUsername(user.getUsername());
        userRepository.save(u);
        return (List<User>)userRepository.findAll();
    }


    @DeleteMapping("/api/user/{id}")
    public List<User> deleteUser
            (@PathVariable("id") int userId) {
        userRepository.deleteById(userId);
        return (List<User>) userRepository.findAll();
    }

}

