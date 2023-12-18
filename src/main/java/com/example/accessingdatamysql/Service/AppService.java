package com.example.accessingdatamysql.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.accessingdatamysql.User;
import com.example.accessingdatamysql.UserRepository;;

@Service
public class AppService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> addService(User user) {
        try {
            if ((user.getName() != "") & (user.getEmail() != "")) {
                userRepository.save(user);
                return ResponseEntity.ok("User added");
            } else {
                return ResponseEntity.badRequest().body("Invalid user name or email");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error!");
        }
    }

    public ResponseEntity<Object> getAllUsers() {
        try {
            Iterable<User> user = userRepository.findAll();
            if (user.iterator().hasNext()) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body("No users are present");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

    public ResponseEntity<Object> getUserById(int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body("Invalid user name or email");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

    public ResponseEntity<String> deleteByID(int id) {
        try {
            Optional<User> checkUserWithId = userRepository.findById(id);
            if (checkUserWithId.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("Deleted the data having id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id:" + id + " doesn't exist!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

    public ResponseEntity<String> updateById(User user, int id) {
        try {
            Optional<User> checkUserWithId = userRepository.findById(id);
            User existingUser = checkUserWithId.orElse(null);
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                userRepository.save(existingUser); // Save the changes to the database
                return ResponseEntity.ok("Updated the data having id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id:" + id + " doesn't exist!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

    public ResponseEntity<Object> getUserByEmail(String email)
    {
        try {
            User userByEmail=userRepository.findByEmail(email);
            if (userByEmail!=null) {
                return ResponseEntity.ok(userByEmail);
            }
            else
            {
                return ResponseEntity.badRequest().body("Invalid email");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

}
