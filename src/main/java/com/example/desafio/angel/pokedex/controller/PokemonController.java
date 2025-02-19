package com.example.desafio.angel.pokedex.controller;

import com.example.desafio.angel.pokedex.exception.PokemonNotFoundException;
import com.example.desafio.angel.pokedex.service.PokeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/pokemons")
@CrossOrigin(origins = "*")
public class PokemonController {

    private final PokeApiService pokeApiService;

    @Autowired
    public PokemonController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Mono<Map>> listPokemons(
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        Mono<Map> response = pokeApiService.getPokemons(limit, offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Mono<Map>> getPokemonDetail(@PathVariable String name) {
        Mono<Map> response = pokeApiService.getPokemonDetail(name)
                .switchIfEmpty(Mono.error(new PokemonNotFoundException(name)));
        return ResponseEntity.ok(response);
    }
}
