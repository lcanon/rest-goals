package org.frontdev2ops.goals.service.impl;

import static javax.transaction.Transactional.TxType.REQUIRED;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.frontdev2ops.goals.service.api.UrlImageService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional(REQUIRED)
public class UrlImageServiceImpl implements UrlImageService {

  public String findRandomPhoto(String search) {
    /*
    Client client = ClientBuilder.newClient();
    Response response  =client.target(
        "https://api.unsplash.com/search/photos?client_id=g4e2UBIae71yWNk9K8ipr8HpOm3b3qkqFiOdbrSMmmM&page=1&query="
            + search + "&page=1&per_page=1").request(MediaType.APPLICATION_JSON).get();

    JSONObject object = (JSONObject) new JSONParser().parse(response.readEntity(String.class));
*/
    return null;
  }

}
