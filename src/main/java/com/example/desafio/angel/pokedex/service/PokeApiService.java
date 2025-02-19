package com.example.desafio.angel.pokedex.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class PokeApiService implements PokeApiServiceInterface{

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2";
    private final WebClient webClient;

    public PokeApiService() {
        this.webClient = WebClient.builder().baseUrl(POKEAPI_BASE_URL).build();
    }

    @Cacheable(value = "pokemons", key = "#offset + '-' + #limit")
    public Mono<Map> getPokemons(int offset, int limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/pokemon")
                        .queryParam("offset", offset)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(Map.class);
    }
    @Cacheable(value = "pokemonDetails", key = "#name")
    public Mono<Map> getPokemonDetail(String name) {
        return webClient.get()
                .uri("/pokemon/{pokemonId}", name)
                .retrieve()
                .bodyToMono(Map.class);
    }
}