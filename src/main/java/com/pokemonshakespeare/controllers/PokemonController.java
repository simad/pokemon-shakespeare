package com.pokemonshakespeare.controllers;


import com.pokemonshakespeare.api.model.ShakespearePokemon;
import com.pokemonshakespeare.services.PokemonService;
import com.pokemonshakespeare.services.ShakespeareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private PokemonService pokemonService;
    private ShakespeareService shakespeareService;

    @Autowired
    public PokemonController(
        PokemonService pokemonService,
        ShakespeareService shakespeareService) {
        this.pokemonService = pokemonService;
        this.shakespeareService = shakespeareService;
    }

    @GetMapping("/{pokemonName}")
    public ShakespearePokemon pokemon(@PathVariable String pokemonName) {
        String pokemonDescription = pokemonService.getPokemonDescriptionInEnglish(pokemonName);
        String translatedDescription = shakespeareService.getTranslatedDescription(pokemonDescription);
        return new ShakespearePokemon(pokemonName, translatedDescription);
    }
    
}
