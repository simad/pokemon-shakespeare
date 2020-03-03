package com.pokemonshakespeare.controllers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pokemonshakespeare.api.exceptions.CustomException;
import com.pokemonshakespeare.clients.pokemon.model.DescriptionEntry;
import com.pokemonshakespeare.clients.pokemon.model.Language;
import com.pokemonshakespeare.clients.pokemon.model.Pokemon;
import com.pokemonshakespeare.clients.pokemon.model.Species;
import com.pokemonshakespeare.clients.shakespeare.model.Contents;
import com.pokemonshakespeare.clients.shakespeare.model.Translation;
import java.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(properties ={
    "pokemon.api.url=http://localhost:8089",
    "shakespeare.api.url=http://localhost:8089"
} )
@AutoConfigureMockMvc
public class PokemonControllerTestIT {

  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

  @Before
  public void setup() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void shouldReturnTranslatedDescriptionForValidPokemon()
      throws Exception {
    Pokemon charizard = new Pokemon(new Pokemon.Species("charizard", "http://localhost:8089/species"));
    stubResponse("/charizard", charizard);

    Species species = new Species(
        Collections.singletonList(
            new DescriptionEntry("description\nwith Special, chars", new Language("en"))));
    stubResponse("/species", species);

    Translation translation = new Translation(new Contents("description with Special, chars"));

    stubResponse("/?text=description%20with%20Special,%20chars", translation);
    this.mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/charizard"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value("charizard"))
        .andExpect(jsonPath("$.description").value("description with Special, chars"));
  }

  @Test
  public void shouldReturnErrorIfApiCallFails() throws Exception {
    stubFor(get(urlEqualTo("/charizard"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    this.mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/charizard"))
        .andExpect(status().isInternalServerError());
  }

  private void stubResponse(String url, Object response) throws JsonProcessingException {
    stubFor(get(urlEqualTo(url))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", APPLICATION_JSON_VALUE)
            .withBody(objectMapper.writeValueAsString(response))));
  }
}