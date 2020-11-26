package org.frontdev2ops.goals.service.impl;

import static javax.transaction.Transactional.TxType.REQUIRED;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.frontdev2ops.goals.service.api.UrlImageService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional(REQUIRED)
public class UrlImageServiceImpl implements UrlImageService {

  @ConfigProperty(name = "photo.api.key")
  String photoApiKey;

  public String findRandomPhoto(String search) {
    log.info("Searching for image {}", search);
    String image = "https://www.flaticon.es/svg/static/icons/svg/1175/1175202.svg";
    try {
      Client client = ClientBuilder.newClient();
      Response response = client.target(
          "https://api.unsplash.com/search/photos?client_id=" + photoApiKey + "&page=1&query="
              + search + "&page=1&per_page=1").request(MediaType.APPLICATION_JSON).get();

      JSONObject object = (JSONObject) new JSONParser().parse(response.readEntity(String.class));
      JSONArray results = (JSONArray) object.get("results");
      JSONObject firstResult = (JSONObject) results.get(0);
      JSONObject urls = (JSONObject) firstResult.get("urls");
      image = (String) urls.get("regular");
    } catch (Exception e) {
      log.info("Cannot get image for {}", search);
    }

    return image;
  }

}
