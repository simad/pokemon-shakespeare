package com.pokemonshakespeare.clients.shakespeare;

import static org.mockito.Mockito.when;

import com.pokemonshakespeare.clients.shakespeare.model.Translation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ShakespeareApiClientTest {
  private final static String SHAKESPEARE_URL = "http://shakespeare.url";
  @Mock
  private RestTemplate restTemplate;

  private ShakespeareApiClient shakespeareApiClient;

  @Before
  public void setup() {
    shakespeareApiClient = new ShakespeareApiClient(restTemplate);
    ReflectionTestUtils.setField(shakespeareApiClient, "shakespeareApiUrl", SHAKESPEARE_URL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldPropagateExceptions() {
    when(restTemplate.getForObject("http://shakespeare.url" + "?text=any", Translation.class))
        .thenThrow(new IllegalArgumentException());
    shakespeareApiClient.getTranslation("any");
  }

}