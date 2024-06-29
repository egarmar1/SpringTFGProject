package com.hackWeb.hackWeb.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GithubService {

    public String getPrimaryEmail(String accessToken) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);
        headers.add("Accept", "application/json");

        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, new URI("https://api.github.com/user/emails"));

        System.out.println("Esta es la request: " + request);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {
        });

        System.out.println("Esta es la response: " + response);

        for (Map<String, Object> emailInfo : Objects.requireNonNull(response.getBody())) {
            Boolean primary = (Boolean) emailInfo.get("primary");
            if (primary != null && primary) {
                return (String) emailInfo.get("email");
            }
        }
        return null;
    }
}
