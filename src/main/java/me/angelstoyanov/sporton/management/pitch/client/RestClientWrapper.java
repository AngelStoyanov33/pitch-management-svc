package me.angelstoyanov.sporton.management.pitch.client;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;
import me.angelstoyanov.sporton.management.pitch.model.StorageEntity;
import me.angelstoyanov.sporton.management.pitch.model.comment.Comment;
import me.angelstoyanov.sporton.management.pitch.model.rating.AverageRatingDTO;
import me.angelstoyanov.sporton.management.pitch.model.rating.Rating;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@ApplicationScoped
@RegisterForReflection
@Named("RestClientWrapper")
public class RestClientWrapper {

    @Inject
    @RestClient
    protected FeedbackManagementClient feedbackManagementClient;

    @Inject
    @RestClient
    protected AzureStorageAdapterClient azureStorageAdapterClient;

    public Rating getRatingByUserAndPitchId(String userId, String pitchId) {
        try {
            Response svcResponse = feedbackManagementClient.getRatingByUserAndPitchId(userId, pitchId);
            if (svcResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return svcResponse.readEntity(Rating.class);
            }
        } catch (WebApplicationException e) {
            return null;
        }
        return null;
    }

    public Double getAverageRating(String pitchId) {
        try {
            Response svcResponse = feedbackManagementClient.getAverageRatingByPitchId(pitchId);
            if (svcResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return svcResponse.readEntity(AverageRatingDTO.class).getAverageRating();
            }
        } catch (WebApplicationException e) {
            return null;
        }
        return null;
    }

    public List<Comment> getAllCommentsByPitchId(String pitchId) {
        try {
            Response svcResponse = feedbackManagementClient.getAllCommentsByPitchId(pitchId);
            if (svcResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return svcResponse.readEntity(new GenericType<>() {});
            }
        } catch (WebApplicationException e) {
            return null;
        }
        return null;
    }

    public List<Rating> getAllRatingsByPitchId(String pitchId) {
        try {
            Response svcResponse = feedbackManagementClient.getAllRatingsByPitchId(pitchId);
            if (svcResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return svcResponse.readEntity(new GenericType<>() {});
            }
        } catch (WebApplicationException e) {
            return null;
        }
        return null;
    }

    public String uploadBlob(@NotNull String entityId, @NotNull StorageEntity entityType, @NotNull File blob) {
        try {
            System.out.println("Uploading blob for entityId: " + entityId + " and entityType: " + entityType);
            Response svcResponse = azureStorageAdapterClient.upload(entityId, String.valueOf(entityType), blob);
            if (svcResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                return svcResponse.readEntity(String.class);
            }
        } catch (WebApplicationException e) {
            return null;
        }
        return null;
    }
}
