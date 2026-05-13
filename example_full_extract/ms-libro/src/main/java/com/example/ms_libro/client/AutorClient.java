package com.example.ms_libro.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_libro.dto.AutorResponse;
import com.example.ms_libro.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutorClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8083/api/autores/";

    public AutorResponse obtenerAutor(Long id, String token) {

        ApiResponse<AutorResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<AutorResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}