package com.pokemonshakespeare.clients.pokemon;

import static java.lang.String.*;

import com.pokemonshakespeare.clients.pokemon.model.Pokemon;
import com.pokemonshakespeare.clients.pokemon.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PokemonApiClient {
  @Value("${pokemon.api.url}")
  private String pokemonApiUrl;

  private RestTemplate restTemplate;

  @Autowired
  public PokemonApiClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Pokemon getPokemon(String pokemonName) {
    final String uri = format("%s/%s", pokemonApiUrl, pokemonName);
    return restTemplate.getForObject(uri, Pokemon.class);
  }

  public Species getSpeciesFor(Pokemon pokemon) {
    return restTemplate.getForObject(pokemon.getSpecies().getUrl(), Species.class);
  }

}
