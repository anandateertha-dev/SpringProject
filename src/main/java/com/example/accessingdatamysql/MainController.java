package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.accessingdatamysql.Service.AppService;

@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private AppService service;

    @PostMapping("add") // Add an user
    public ResponseEntity<Object> addNewUser(@RequestBody User user) {
        return service.addService(user);
    }

    @GetMapping(path = "all") // Get all the users
    public ResponseEntity<Object> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("byid/{id}") // Get the user with Id
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        return service.getUserById(id);
    }

    @DeleteMapping("deletebyid/{id}") // Delete the user
    public ResponseEntity<String> deletebyid(@PathVariable int id) {
        return service.deleteByID(id);
    }

    @PutMapping("updatebyid/{id}") // Update the user
    public ResponseEntity<String> updateById(@RequestBody User user,@PathVariable int id) {
        return service.updateById(user,id);

    }

    @GetMapping("getbyemail/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email)
    {
        return service.getUserByEmail(email);
    }
}