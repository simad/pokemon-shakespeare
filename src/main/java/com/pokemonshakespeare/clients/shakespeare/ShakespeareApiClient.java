package com.pokemonshakespeare.clients.shakespeare;

import com.pokemonshakespeare.clients.shakespeare.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShakespeareApiClient {
  @Value("${shakespeare.api.url}")
  private String shakespeareApiUrl;

  private RestTemplate restTemplate;

  @Autowired
  public ShakespeareApiClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Translation getTranslation(String description) {
    String request = String.format("%s?text=%s", shakespeareApiUrl, description);
    return restTemplate.getForObject(request, Translation.class);
  }
}
