package com.example.desafio.angel.pokedex.controller;

import com.example.desafio.angel.pokedex.exception.PokemonNotFoundException;
import com.example.desafio.angel.pokedex.service.PokeApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PokemonControllerTest {

    @Mock
    private PokeApiService pokeApiService;

    @InjectMocks
    private PokemonController pokemonController;

    @Before
    public void setUp() {
        // Setup before each test if needed
    }

    @Test
    public void testGetPokemonDetail_Success() {
        // Given
        String pokemonName = "pikachu";
        Map<String, Object> pokemonDetail = Map.of("name", pokemonName, "type", "electric");

        // Mock service call
        Mockito.when(pokeApiService.getPokemonDetail(pokemonName))
                .thenReturn(Mono.just(pokemonDetail));

        // When
        ResponseEntity<Mono<Map>> response = pokemonController.getPokemonDetail(pokemonName);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().block() instanceof Map);
    }

    @Test
    public void testGetPokemonDetail_NotFound() {
        // Given
        String pokemonName = "nonexistentpokemon";

        // Mock service call to return an empty Mono
        Mockito.when(pokeApiService.getPokemonDetail(pokemonName))
                .thenReturn(Mono.empty());

        // When
        ResponseEntity<Mono<Map>> response = pokemonController.getPokemonDetail(pokemonName);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        response.getBody().block();
        assertTrue(false);
    }
}
