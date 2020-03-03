package com.pokemonshakespeare.api.model;

import java.util.Objects;

public class ShakespearePokemon {

  private String name;
  private String description;

  public ShakespearePokemon(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShakespearePokemon that = (ShakespearePokemon) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}
