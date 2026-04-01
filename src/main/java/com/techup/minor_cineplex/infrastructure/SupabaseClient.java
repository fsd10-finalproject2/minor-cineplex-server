package com.techup.minor_cineplex.infrastructure;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.techup.minor_cineplex.exception.AppException;
import com.techup.minor_cineplex.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupabaseClient {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}") private String supabaseUrl;
    @Value("${supabase.apiKey}") private String supabaseServiceKey;
    @Value("${supabase.anon.key}") private String supabaseAnonKey;

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
        new ParameterizedTypeReference<Map<String, Object>>() {};

    public Map<String, Object> signUp(String email, String password) {
        return post("/auth/v1/signup",
            Map.of("email", email, "password", password),
            supabaseAnonKey,
            ErrorCode.USER_ALREADY_EXISTS
        );
    }

    public Map<String, Object> signIn(String email, String password) {
        return post("/auth/v1/token?grant_type=password",
            Map.of("email", email, "password", password),
            supabaseAnonKey,
            ErrorCode.INVALID_CREDENTIALS
        );
    }

    public void updateUser(UUID userId, Map<String, String> fields) {
        put("/auth/v1/admin/users/" + userId, fields, supabaseServiceKey);
    }

    public void updateUserWithToken(String accessToken, Map<String, String> fields) {
        try {
            restTemplate.exchange(
                supabaseUrl + "/auth/v1/user", HttpMethod.PUT,
                new HttpEntity<>(fields, userHeaders(accessToken)), MAP_TYPE
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Supabase error on /auth/v1/user: {}", e.getResponseBodyAsString());
            throw new AppException(ErrorCode.UPDATE_FAILED);
        } catch (RestClientException e) {
            log.error("Supabase connection error: {}", e.getMessage());
            throw new AppException(ErrorCode.AUTH_SERVICE_UNAVAILABLE);
        }
    }

    private Map<String, Object> post(String path, Map<String, String> body, String key, ErrorCode onClientError) {
        try {
            ResponseEntity<Map<String, Object>> res = restTemplate.exchange(
                supabaseUrl + path, HttpMethod.POST,
                new HttpEntity<>(body, headers(key)), MAP_TYPE
            );
            return res.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Supabase error on {}: {}", path, e.getResponseBodyAsString());
            throw new AppException(onClientError);
        } catch (RestClientException e) {
            log.error("Supabase connection error on {}: {}", path, e.getMessage());
            throw new AppException(ErrorCode.AUTH_SERVICE_UNAVAILABLE);
        }
    }

    private void put(String path, Map<String, String> body, String key) {
        try {
            restTemplate.exchange(
                supabaseUrl + path, HttpMethod.PUT,
                new HttpEntity<>(body, headers(key)), MAP_TYPE
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Supabase error on {}: {}", path, e.getResponseBodyAsString());
            throw new AppException(ErrorCode.UPDATE_FAILED);
        } catch (RestClientException e) {
            log.error("Supabase connection error on {}: {}", path, e.getMessage());
            throw new AppException(ErrorCode.AUTH_SERVICE_UNAVAILABLE);
        }
    }

    // admin api call
    private HttpHeaders headers(String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", key);
        headers.set("Authorization", "Bearer " + key);
        return headers;
    }
    // user api call
    private HttpHeaders userHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseAnonKey);
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }
}