package com.pokemonshakespeare.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.pokemonshakespeare.api.exceptions.CustomException;
import com.pokemonshakespeare.clients.pokemon.PokemonApiClient;
import com.pokemonshakespeare.clients.pokemon.model.DescriptionEntry;
import com.pokemonshakespeare.clients.pokemon.model.Language;
import com.pokemonshakespeare.clients.pokemon.model.Pokemon;
import com.pokemonshakespeare.clients.pokemon.model.Species;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PokemonServiceTest {

  @Mock
  private PokemonApiClient pokemonApiClient;

  private PokemonService pokemonService;
  @Before
  public void setup() {
    pokemonService = new PokemonService(pokemonApiClient);
  }

  @Test
  public void shouldRetrieveAPokemonDescriptionInEnglish() {
    // in a real project all of these data would probably be separated into test doubles / test
    // data generators to avoid cluttering the test cases themselves
    DescriptionEntry expectedDescription =
        new DescriptionEntry("correct", new Language("en"));
    List<DescriptionEntry> descriptionEntries = Arrays.asList(
        new DescriptionEntry("wrong", new Language("jp")),
        expectedDescription,
        new DescriptionEntry("wrong again", new Language("it"))
    );
    Pokemon pokemon = new Pokemon();
    Species species = new Species(descriptionEntries);
    when(pokemonApiClient.getPokemon("pokemon")).thenReturn(pokemon);
    when(pokemonApiClient.getSpeciesFor(pokemon)).thenReturn(species);
    String description = pokemonService.getPokemonDescriptionInEnglish("pokemon");

    assertEquals(expectedDescription.getText(), description);
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfThereIsNoDescriptionInEnglish() {
    List<DescriptionEntry> descriptionEntries = Arrays.asList(
        new DescriptionEntry("wrong", new Language("jp")),
        new DescriptionEntry("wrong again", new Language("it"))
    );
    Pokemon pokemon = new Pokemon();
    Species species = new Species(descriptionEntries);
    when(pokemonApiClient.getPokemon("pokemon")).thenReturn(pokemon);
    when(pokemonApiClient.getSpeciesFor(pokemon)).thenReturn(species);
    pokemonService.getPokemonDescriptionInEnglish("pokemon");
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfNoDescriptionsAreAvaliable() {
    Pokemon pokemon = new Pokemon();
    Species species = new Species(null);
    when(pokemonApiClient.getPokemon("pokemon")).thenReturn(pokemon);
    when(pokemonApiClient.getSpeciesFor(pokemon)).thenReturn(species);
    pokemonService.getPokemonDescriptionInEnglish("pokemon");
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfClientErrors() {
    when(pokemonApiClient.getPokemon("pokemon")).thenThrow(new RuntimeException());
    pokemonService.getPokemonDescriptionInEnglish("pokemon");
  }

}