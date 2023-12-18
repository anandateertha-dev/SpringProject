package com.example.accessingdatamysql;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add") // Add an user
    public ResponseEntity<String> addNewUser(@RequestBody User user) {

        try {
            if ((user.getName() != "")&(user.getEmail()!="")) {
                userRepository.save(user);
                return ResponseEntity.ok("User added");
            } else {
                return ResponseEntity.badRequest().body("Invalid user name or email");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }
    }

    @GetMapping(path = "all") // Get all the user
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

    @GetMapping("byid/{id}") // Get the user with Id
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
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

    @DeleteMapping("deletebyid/{id}") // Delete the user
    public ResponseEntity<String> deletebyid(@PathVariable int id) {
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

    @PutMapping("updatebyid/{id}") // Update the user
    public ResponseEntity<String> updateById(@PathVariable int id, User user) {

        try {
            Optional<User> checkUserWithId = userRepository.findById(id);
            if (checkUserWithId.isPresent()) {
                userRepository.save(user);
                return ResponseEntity.ok("Updated the data having id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id:" + id + " doesn't exist!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!");
        }

    }
}