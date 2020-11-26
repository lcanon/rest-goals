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
import javax.ws.rs.PUT;
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
import org.frontdev2ops.goals.service.api.UrlImageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/api/users/{user-id}/goals")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GoalResource {

  @Inject
  GoalService goalService;

  @Inject
  UrlImageService urlImageService;

  @GET
  @Operation(summary = "Returns all goals of a user")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
  @APIResponse(responseCode = "204", description = "The goal is not found")
  public Response getAllGoals(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId) {
    List<Goal> goals = goalService.findAllOfUser(userId);
    log.info("Get all Goals of user {}: {}", userId, goals);


    //urlImageService.findRandomPhoto("iphone");
    return Response.ok(goals).build();
  }

  @POST
  @Operation(summary = "Creates a valid goal for a user")
  @APIResponse(responseCode = "201", description = "The URI of the created goal", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
  public Response createGoal(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId,
      @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
      @Valid Goal goal, @Context UriInfo uriInfo) {
    goal.userId = userId;
    goal.actual = goal.total;
    goal = goalService.save(goal);

    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(goal.id));
    log.info("New goal created with URI " + builder.build().toString());
    return Response.created(builder.build()).build();
  }

  @GET
  @Path("/{goal-id}")
  @Operation(summary = "Returns a goal of a user from its ID")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
  @APIResponse(responseCode = "204", description = "The goal is not found")
  public Response getGoal(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId,
      @Parameter(description = "Goal id", required = true) @PathParam("goal-id") Long goalId) {
    Optional<Goal> goal = goalService.findById(goalId);

    if (goal.isPresent()) {
      log.info("Found goal of user {}: {}", userId, goal);
      return Response.ok(goal.get()).build();
    }
    log.info("Goal of user {} with id {} not found", userId, goalId);
    return Response.noContent().build();
  }

  @PUT
  @Path("/{goal-id}")
  @Operation(summary = "Update a goal of a user")
  @APIResponse(responseCode = "200", description = "The URI of the updated goal", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
  public Response updateGoal(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId,
      @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
      @Valid Goal goal, @Context UriInfo uriInfo) {
    goal.userId = userId;
    goal = goalService.update(goal);

    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(goal.id));
    log.info("New goal created with URI " + builder.build().toString());
    return Response.ok(builder.build()).build();
  }


  @DELETE
  @Path("/{goal-id}")
  @Operation(summary = "Deletes an exiting goal of a user")
  @APIResponse(responseCode = "204", description = "The goal was deleted")
  public Response deleteGoal(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId,
      @Parameter(description = "Goal id", required = true) @PathParam("goal-id") Long goalId) {
    goalService.delete(goalId);
    log.info("Goal {} of user {} deleted", goalId, userId);
    return Response.noContent().build();
  }

  @PUT
  @Path("/{goal-id}/tips/{tip-amount}")
  @Operation(summary = "Add a tip to an exiting goal of a user")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Goal.class)))
  @APIResponse(responseCode = "204", description = "The goal is not found")
  public Response add(
      @Parameter(description = "User id", required = true) @PathParam("user-id") Long userId,
      @Parameter(description = "Goal id", required = true) @PathParam("goal-id") Long goalId,
      @Parameter(description = "Tip Amount", required = true) @PathParam("tip-amount") Double tipAmount) {

    Optional<Goal> goal = goalService.addTip(goalId, tipAmount);


    if (goal.isPresent()) {
      log.info("Goal {} of user {} tip {}", goalId, userId, tipAmount);
      return Response.ok(goal.get()).build();
    }
    log.info("Goal of user {} with id {} not found", userId, goalId);
    return Response.noContent().build();
  }

}
