package org.frontdev2ops.goals.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.frontdev2ops.goals.domain.Goal;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GoalResourceTest {

  @Test
  void shouldPingOpenAPI() {
    given()
        .header(ACCEPT, APPLICATION_JSON)
        .when().get("/openapi")
        .then()
        .statusCode(OK.getStatusCode());
  }

  @Test
  void shouldPingSwaggerUI() {
    given()
        .when().get("/swagger-ui")
        .then()
        .statusCode(OK.getStatusCode());
  }

  @Test
  @Order(1)
  void shouldGetInitialItems() {
    List<Goal> goals = get("/goals").then()
        .statusCode(OK.getStatusCode())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .extract().body().as(getGoalTypeRef());
    assertEquals(0, goals.size());
  }


  private TypeRef<List<Goal>> getGoalTypeRef() {
    return new TypeRef<List<Goal>>() {
      // Kept empty on purpose
    };
  }
}