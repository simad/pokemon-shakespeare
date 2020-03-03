package com.pokemonshakespeare.clients.pokemon;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.pokemonshakespeare.clients.pokemon.model.Pokemon;
import com.pokemonshakespeare.clients.pokemon.model.Species;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class PokemonApiClientTest {

  private final static String POKEMON_URL = "http://pokemon.url";
  @Mock
  private RestTemplate restTemplate;

  private PokemonApiClient pokemonApiClient;

  @Before
  public void setup() {
    pokemonApiClient = new PokemonApiClient(restTemplate);
    ReflectionTestUtils.setField(pokemonApiClient, "pokemonApiUrl", POKEMON_URL);
  }

  @Test
  public void shouldCallCorrectUrlToRetrieveAPokemon() {
    Pokemon pokemon = new Pokemon();

    when(restTemplate.getForObject(POKEMON_URL + "/coffeemon", Pokemon.class))
        .thenReturn(pokemon);
    Pokemon returnedPokemon = pokemonApiClient.getPokemon("coffeemon");
    assertEquals(returnedPokemon, pokemon);
  }

  @Test
  public void shouldCallCorrectUrlToRetrieveAPokemonsSpecies() {
    String speciesUrl = "http://species.url";
    Pokemon.Species pokemonSpecies = new Pokemon.Species("aaa", speciesUrl);
    Pokemon pokemon = new Pokemon(pokemonSpecies);
    Species species = new Species();
    when(restTemplate.getForObject(speciesUrl, Species.class))
        .thenReturn(species);
    Species returnedSpecies = pokemonApiClient.getSpeciesFor(pokemon);
    assertEquals(species, returnedSpecies);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldPropagateExceptions() {
    when(restTemplate.getForObject(POKEMON_URL + "/coffeemon", Pokemon.class))
        .thenThrow(new IllegalArgumentException());
    pokemonApiClient.getPokemon("coffeemon");
  }

}