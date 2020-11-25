package org.frontdev2ops.goals.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.frontdev2ops.goals.domain.Goal;
import org.frontdev2ops.goals.service.api.GoalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/goals")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GoalResource {

  @Inject
  GoalService goalService;

  @GET
  @Operation(summary = "Returns all goals")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
  @APIResponse(responseCode = "204", description = "The goal is not found")
  public Response getAllGoals() {
    List<Goal> goals = goalService.findAll();
    log.info("Get all Goals: {}", goals);
    return Response.ok(goals).build();
  }

  @GET
  @Path("/{goal-id}")
  @Operation(summary = "Returns a goal from ID")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
  @APIResponse(responseCode = "204", description = "The goal is not found")
  public Response getGoal(
      @Parameter(description = "Goal id", required = true) @PathParam("goal-id") Long id) {
    Optional<Goal> goal = goalService.findById(id);

    if (goal.isPresent()) {
      log.info("Found goal: {}", goal);
      return Response.ok(goal.get()).build();
    }
    log.info("Goal with id {} not found", id);
    return Response.noContent().build();
  }

  @POST
  @Operation(summary = "Creates a valid goal")
  @APIResponse(responseCode = "201", description = "The URI of the created goal", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
  public Response createGoal(
      @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
      @Valid Goal goal, @Context UriInfo uriInfo) {
    goal = goalService.save(goal);

    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(goal.id));
    log.info("New goal created with URI " + builder.build().toString());
    return Response.created(builder.build()).build();
  }

  @DELETE
  @Path("/{goal-id}")
  @Operation(summary = "Deletes an exiting goal")
  @APIResponse(responseCode = "204", description = "The goal was deleted")
  public Response deleteGoal(
      @Parameter(description = "Goal id", required = true) @PathParam("goal-id") Long id) {
    goalService.delete(id);
    log.info("Goal deleted with " + id);
    return Response.noContent().build();
  }

}
