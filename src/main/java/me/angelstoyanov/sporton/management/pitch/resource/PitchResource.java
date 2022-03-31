package me.angelstoyanov.sporton.management.pitch.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;
import me.angelstoyanov.sporton.management.pitch.client.RestClientWrapper;
import me.angelstoyanov.sporton.management.pitch.exception.PitchAlreadyExistsException;
import me.angelstoyanov.sporton.management.pitch.exception.PitchNotExistsException;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.model.StorageEntity;
import me.angelstoyanov.sporton.management.pitch.model.extended.ExtendedPitch;
import me.angelstoyanov.sporton.management.pitch.osm.adapter.OSMAPIAdapter;
import me.angelstoyanov.sporton.management.pitch.repository.PitchRepository;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
public class PitchResource {

    @Inject
    protected PitchRepository pitchRepository;

    @Inject
    protected OSMAPIAdapter osmapiAdapter;

    @Inject
    protected ObjectMapper objectMapper;

    @Inject
    protected RestClientWrapper restClient;

    @POST
    @ResponseStatus(201)
    @Path("/pitch")
    public RestResponse<Pitch> createPitch(Pitch pitch,
                                           @HeaderParam("X-Requesting-User-Role") String userRole) {
        if(!userRole.equals("ADMIN")) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.FORBIDDEN).build();
        }
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

    @PATCH
    @ResponseStatus(200)
    @Consumes({"image/jpeg,image/png,image/bmp"})
    @Path("/pitch/{id}/blob")
    public RestResponse<Pitch> updatePitchBlob(@PathParam("id") String id,
                                               File attachment,
                                               @HeaderParam("X-Requesting-User-Role") String userRole) {
        if(!userRole.equals("ADMIN")) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.FORBIDDEN).build();
        }
        try {
            Pitch pitch = pitchRepository.findByPitchId(new ObjectId(id));
            if (attachment.length() != 0) {
                String attachmentUrl = this.uploadAttachment(attachment, id);
                if (attachmentUrl != null) {
                    pitch.setAttachment(attachmentUrl);
                }
            }
            return RestResponse.ResponseBuilder.ok(pitchRepository.replacePitch(new ObjectId(id), pitch)).build();
        } catch (PitchNotExistsException e) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.NOT_FOUND).build();
        }
    }

    @GET
    @ResponseStatus(200)
    @Path("/pitch/{id}")
    public RestResponse<String> getPitch(@PathParam("id") String id,
                                         @DefaultValue("narrow") @QueryParam("output") String output,
                                         @QueryParam("include") List<String> include,
                                         @HeaderParam("X-Requesting-User-Id") String userId) throws JsonProcessingException {
        Pitch pitch = pitchRepository.findById(new ObjectId(id));
        if (pitch == null) {
            return RestResponse.ResponseBuilder.ok((String) null).status(RestResponse.Status.NOT_FOUND).build();
        }
        if (output.equalsIgnoreCase("narrow")) {
            return RestResponse.ResponseBuilder.ok(objectMapper.writeValueAsString(pitch)).build();
        } else if (output.equalsIgnoreCase("wide") && include != null) {
            ExtendedPitch extendedPitch = new ExtendedPitch(pitch);
            if (include.contains("average_rating")) {
                extendedPitch.setAverageRating(restClient.getAverageRating(id));
            }
            if (include.contains("ratings")) {
                extendedPitch.setRatings(restClient.getAllRatingsByPitchId(id));
            }
            if (include.contains("my_rating")) {
                extendedPitch.setMyRating(restClient.getRatingByUserAndPitchId(userId, id));
            }
            if (include.contains("comments")) {
                extendedPitch.setComments(restClient.getAllCommentsByPitchId(id));
            }
            return RestResponse.ResponseBuilder.ok(objectMapper.writeValueAsString(extendedPitch)).build();
        }
        return RestResponse.ResponseBuilder.ok((String) null).status(RestResponse.Status.BAD_REQUEST).build();
    }

    @GET
    @ResponseStatus(200)
    @Path("/pitch/locate")
    public RestResponse<List<Pitch>> getPitchesNearMe(@NotNull @QueryParam("latitude") double lat,
                                                      @NotNull @QueryParam("longitude") double lon,
                                                      @NotNull @QueryParam("radius") double radius,
                                                      @QueryParam("type") PitchType type) {
        List<Pitch> pitches;
        if (type != null) {
            pitches = pitchRepository.findPitchesNearMe(lat, lon, radius, type);
        } else {
            pitches = pitchRepository.findPitchesNearMe(lat, lon, radius);
        }
        return RestResponse.ResponseBuilder.ok(pitches).build();
    }


    @DELETE
    @ResponseStatus(200)
    @Path("/pitch/{id}")
    public RestResponse<Pitch> deletePitch(@PathParam("id") String id,
                                           @HeaderParam("X-Requesting-User-Role") String userRole) {
        if(!userRole.equals("ADMIN")) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.FORBIDDEN).build();
        }
        try {
            pitchRepository.deletePitchById(new ObjectId(id));
            return RestResponse.ResponseBuilder.ok((Pitch) null).build();
        } catch (PitchNotExistsException e) {
            return RestResponse.ResponseBuilder.ok((Pitch) null).status(RestResponse.Status.NOT_FOUND).build();
        }
    }

    @GET
    @ResponseStatus(200)
    @Path("/pitch/all")
    public RestResponse<List<Pitch>> getPitches(@NotNull @QueryParam("region") String region,
                                                @QueryParam("type") PitchType type) {
        List<Pitch> pitches;
        if (type != null) {
            pitches = pitchRepository.findPitchesByRegionAndType(region, type);
        } else {
            pitches = pitchRepository.findPitchesByRegion(region);
        }
        return RestResponse.ResponseBuilder.ok(pitches).build();
    }

    private String uploadAttachment(File inputAttachment, String pitchId) {
        String attachment = null;
        if (inputAttachment != null && pitchId != null) {
            attachment = restClient.uploadBlob(pitchId, StorageEntity.IMAGE_PITCH, inputAttachment);
            if (attachment.contains("successfully")) {
                attachment = "/blob/p/" + pitchId;
            }
        }
        return attachment;
    }

}
