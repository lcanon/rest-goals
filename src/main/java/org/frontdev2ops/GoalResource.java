package org.frontdev2ops;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/goal")
public class GoalResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello I'm working";
  }
}
