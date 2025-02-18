package com.example.desafio.angel.pokedex.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface PokeApiServiceInterface {

    Mono<Map> getPokemons(int limit, int offset);


    Mono<Map> getPokemonDetail(String name);
}
