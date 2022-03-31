package me.angelstoyanov.sporton.management.pitch.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import me.angelstoyanov.sporton.management.pitch.repository.PitchRepository;
import me.angelstoyanov.sporton.management.pitch.update.PitchUpdater;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
public class AdminPitchResource {

    @Inject
    protected PitchRepository pitchRepository;

    @Inject
    protected PitchUpdater pitchUpdater;

    @GET
    @ResponseStatus(200)
    @Path("/pitch/all/update")
    public RestResponse<String> getPitch(@HeaderParam("X-Requesting-User-Role") String userRole) {
        if (!userRole.equals("ADMIN")) {
            return RestResponse.ResponseBuilder.ok((String) null).status(RestResponse.Status.FORBIDDEN).build();
        }
        String responseBody = "{\"message\": \"Pitches update already working\"}";
        pitchUpdater.updatePitches();

        return RestResponse.ResponseBuilder.ok(responseBody).build();
    }
}
