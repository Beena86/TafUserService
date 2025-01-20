package com.techarch.TafUserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${taf.datastore.service.url}")
    private String datastoreUrl;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Object user) {
        return restTemplate.postForEntity(datastoreUrl + "/users", user, Object.class);
    }

    @GetMapping("/{id}")
    public Object getUser(@PathVariable Long id) {
        return restTemplate.getForObject(datastoreUrl + "/users/" + id, Object.class);
    }
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody Object user) {
        restTemplate.put(datastoreUrl + "/users/" + id, user);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAllUsers() {
        // Sending GET request to the Datastore service to get all users
        ResponseEntity<List<Object>> response = restTemplate.exchange(
                datastoreUrl + "/users", // URL to get all users from Datastore service
                org.springframework.http.HttpMethod.GET,
                null, // No body in the request
                new org.springframework.core.ParameterizedTypeReference<List<Object>>() {} // List of users as the response body
        );

        // Returning the list of users as the response
        return ResponseEntity.ok(response.getBody());
    }
}
