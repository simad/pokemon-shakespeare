package com.pokemonshakespeare.clients.pokemon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Species {

  @JsonProperty("flavor_text_entries")
  private List<DescriptionEntry> descriptions;

  public List<DescriptionEntry> getDescriptions() {
    return descriptions;
  }

  public Species(List<DescriptionEntry> descriptions) {
    this.descriptions = descriptions;
  }

  public Species() {
  }
}
