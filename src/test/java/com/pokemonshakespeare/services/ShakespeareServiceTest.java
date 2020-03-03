package com.pokemonshakespeare.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.pokemonshakespeare.api.exceptions.CustomException;
import com.pokemonshakespeare.clients.shakespeare.ShakespeareApiClient;
import com.pokemonshakespeare.clients.shakespeare.model.Contents;
import com.pokemonshakespeare.clients.shakespeare.model.Translation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShakespeareServiceTest {

  @Mock
  private ShakespeareApiClient shakespeareApiClient;

  private ShakespeareService shakespeareService;
  @Before
  public void setup() {
    shakespeareService = new ShakespeareService(shakespeareApiClient);
  }

  @Test
  public void shouldTranslateAString() {
    // in a real project all of these data would probably be separated into test doubles / test
    // data generators to avoid cluttering the test cases themselves
    String expectedTranslation = "translated bla";
    Translation translation = new Translation(new Contents(expectedTranslation));
    when(shakespeareApiClient.getTranslation("bla bla")).thenReturn(translation);
    String translatedDescription = shakespeareService.getTranslatedDescription("bla bla");
    assertEquals(expectedTranslation, translatedDescription);
  }

  @Test(expected = CustomException.class)
  public void shouldThrowCustomExceptionIfClientErrors() {
    when(shakespeareApiClient.getTranslation("")).thenThrow(new RuntimeException());
    shakespeareService.getTranslatedDescription("");
  }

  @Test(expected = CustomException.class)
  public void shouldThrowIfNoDescriptionContentsAreAvaliable() {
    Translation translation = new Translation(null);
    when(shakespeareApiClient.getTranslation("bla")).thenReturn(translation);
    shakespeareService.getTranslatedDescription("bla");
  }
}