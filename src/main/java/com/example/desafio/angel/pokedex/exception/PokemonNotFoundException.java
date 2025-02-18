package com.example.desafio.angel.pokedex.exception;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String name) {
        super("Pokemon with name " + name + " not found");
    }
}
