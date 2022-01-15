package me.angelstoyanov.sporton.management.pitch.resource;

import me.angelstoyanov.sporton.management.pitch.exception.PitchAlreadyExistsException;
import me.angelstoyanov.sporton.management.pitch.exception.PitchNotExistsException;
import me.angelstoyanov.sporton.management.pitch.model.GeoPoint;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.repository.PitchRepository;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PitchResource {

    @Inject
    protected PitchRepository pitchRepository;

    @POST
    @ResponseStatus(201)
    @Path("/pitch")
    public RestResponse<Pitch> createPitch(Pitch pitch) {
        try {
            Pitch newPitch = pitchRepository.addPitch(pitch);
            return RestResponse.ResponseBuilder.ok(newPitch).build();
        } catch (PitchAlreadyExistsException e) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.CONFLICT).build();
        }
    }

    @PUT
    @ResponseStatus(200)
    @Path("/pitch/{id}")
    public RestResponse<Pitch> updatePitch(@PathParam("id") String id, Pitch pitch) {
        try {
            return RestResponse.ResponseBuilder.ok(pitchRepository.replacePitch(new ObjectId(id), pitch)).build();
        } catch (PitchAlreadyExistsException e) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.NOT_FOUND).build();
        }
    }

    @GET
    @ResponseStatus(200)
    @Path("/pitch/{id}")
    public RestResponse<Pitch> getPitch(@PathParam("id") String id) {
        Pitch pitch = pitchRepository.findById(new ObjectId(id));
        if (pitch == null) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.NOT_FOUND).build();
        }
        return RestResponse.ResponseBuilder.ok(pitch).build();
    }

    @GET
    @ResponseStatus(200)
    @Path("/pitch")
    public Pitch getPitchByLocation(List<GeoPoint> location) {

        if (location != null) {
            return pitchRepository.findByLocation(location);
        }
        return null;
    }

    @GET
    @ResponseStatus(200)
    @Path("/test")
    public RestResponse<Pitch> test() {
        return RestResponse.ResponseBuilder.ok(new Pitch("Test pitch", PitchType.FOOTBALL,
                Arrays.asList(new GeoPoint(42.650050, 23.339383), new GeoPoint(42.650235, 23.339398), new GeoPoint(42.650256, 23.338904), new GeoPoint(42.650078, 23.338893))
        )).status(RestResponse.Status.OK).build();
    }

    @DELETE
    @ResponseStatus(200)
    @Path("/pitch/{id}")
    public RestResponse<Pitch> deletePitch(@PathParam("id") String id) {
        try {
            pitchRepository.deletePitchById(new ObjectId(id));
            return RestResponse.ResponseBuilder.ok((Pitch) null).build();
        } catch (PitchNotExistsException e) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.NOT_FOUND).build();
        }
    }

}
