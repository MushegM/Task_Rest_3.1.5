package ru.kata.spring.boot_security.demo;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.List;

public class NewRestTeamplate {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static String sessionId;

    public static void main(String[] args) {

        List<User> users = getAllUsers();
        System.out.println("All users: " + users);


        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 30);
        String createResponse = createUser(newUser);
        System.out.println("Create response: " + createResponse);


        User updatedUser = new User();
        updatedUser.setId(3L);
        updatedUser.setName("Thomas");
        updatedUser.setLastName("Shelby");
        updatedUser.setAge((byte) 30);
        String updateResponse = updateUser(updatedUser);
        System.out.println("Update response: " + updateResponse);


        String deleteResponse = deleteUser(3L);
        System.out.println("Delete response: " + deleteResponse);
    }

    private static List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
        sessionId = response.getHeaders().getFirst("Set-Cookie");
        return Arrays.asList(response.getBody());
    }

    private static String createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);
        return response.getBody();
    }

    private static String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    private static String deleteUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }
}
