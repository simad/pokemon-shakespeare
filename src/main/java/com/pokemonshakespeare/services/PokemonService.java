package com.pokemonshakespeare.services;

import com.pokemonshakespeare.api.exceptions.CustomException;
import com.pokemonshakespeare.clients.pokemon.model.DescriptionEntry;
import com.pokemonshakespeare.clients.pokemon.model.Pokemon;
import com.pokemonshakespeare.clients.pokemon.PokemonApiClient;
import com.pokemonshakespeare.clients.pokemon.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

  private PokemonApiClient pokemonApiClient;

  @Autowired
  public PokemonService(PokemonApiClient pokemonApiClient) {
    this.pokemonApiClient = pokemonApiClient;
  }

  public String getPokemonDescriptionInEnglish(String pokemonName) {
    // makes two api calls for each pokemon we want to retrieve - not efficient but works
    // as a basic example
    try {
      Pokemon pokemon = pokemonApiClient.getPokemon(pokemonName);
      Species species = pokemonApiClient.getSpeciesFor(pokemon);
      return species.getDescriptions()
          .stream()
          .filter(descriptionEntry -> descriptionEntry.getLanguage().getName().equals("en"))
          .map(DescriptionEntry::getText)
          .map(this::removeSpecialChars)
          .findFirst()
          .orElseThrow(CustomException::new);
    } catch (Exception e) {
      // in production code we might want a specific exception handler / message etc.
      throw new CustomException();
    }
  }

  private String removeSpecialChars(String original) {
    return original.replaceAll("\r", " ").replaceAll("\n", " ").replaceAll("\t", " s");
  }
}
