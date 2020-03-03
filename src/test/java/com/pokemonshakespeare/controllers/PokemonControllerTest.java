package com.pokemonshakespeare.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.pokemonshakespeare.api.exceptions.CustomException;
import com.pokemonshakespeare.api.model.ShakespearePokemon;
import com.pokemonshakespeare.services.PokemonService;
import com.pokemonshakespeare.services.ShakespeareService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PokemonControllerTest {

  @Mock
  private PokemonService pokemonService;
  @Mock
  private ShakespeareService shakespeareService;

  private PokemonController pokemonController;

  @Before
  public void setUp() {
    pokemonController = new PokemonController(pokemonService, shakespeareService);
  }

  @Test
  public void shouldReturnAValidPokemonResponse() {
    String pokemonName = "a pokemon";
    String originalDescription = "description";
    String translatedDescription = "translated description";
    when(pokemonService.getPokemonDescriptionInEnglish(pokemonName))
        .thenReturn(originalDescription);
    when(shakespeareService.getTranslatedDescription(originalDescription))
        .thenReturn(translatedDescription);
    ShakespearePokemon expectedResponse = new ShakespearePokemon(
      pokemonName,
      translatedDescription
    );
    ShakespearePokemon pokemonResponse = pokemonController.pokemon(pokemonName);
    assertEquals(expectedResponse, pokemonResponse);
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfPokemonServiceFails() {
    String pokemonName = "a pokemon";
    when(pokemonService.getPokemonDescriptionInEnglish(pokemonName))
        .thenThrow(new CustomException());
    pokemonController.pokemon(pokemonName);
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfShakespeareServiceFails() {
    String pokemonName = "a pokemon";
    when(pokemonService.getPokemonDescriptionInEnglish(pokemonName))
        .thenReturn("desc");
    when(shakespeareService.getTranslatedDescription("desc"))
        .thenThrow(new CustomException());
    pokemonController.pokemon(pokemonName);
  }
}