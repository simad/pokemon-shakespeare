package com.pokemonshakespeare.clients.pokemon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DescriptionEntry {
  @JsonProperty("flavor_text")
  private String text;
  private Language language;

  public String getText() {
    return text;
  }

  public Language getLanguage() {
    return language;
  }

  public DescriptionEntry() {
  }

  public DescriptionEntry(String text, Language language) {
    this.text = text;
    this.language = language;
  }
}
