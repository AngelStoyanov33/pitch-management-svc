package me.angelstoyanov.sporton.management.pitch.client;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RegisterForReflection
@RegisterRestClient
@ApplicationScoped
public interface FeedbackManagementClient {

    @GET
    @Path("/comment/all")
    Response getAllCommentsByPitchId(@QueryParam("pitch_id") String pitchId);

    @GET
    @Path("/rating/all")
    Response getAllRatingsByPitchId(@QueryParam("pitch_id") String pitchId);

    @GET
    @Path("/rating/average")
    Response getAverageRatingByPitchId(@QueryParam("pitch_id") String pitchId);

    @GET
    @Path("/rating")
    Response getRatingByUserAndPitchId(@QueryParam("user_id") String userId, @QueryParam("pitch_id") String pitchId);
}
