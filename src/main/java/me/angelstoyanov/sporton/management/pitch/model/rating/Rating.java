package me.angelstoyanov.sporton.management.pitch.model.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.types.ObjectId;

@JsonRootName("rating")
@JsonPropertyOrder({"id", "created_at", "user_id", "pitch_id", "rating"})
@RegisterForReflection
public class Rating {
    @JsonProperty("id")
    private ObjectId id;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("pitch_id")
    private ObjectId pitchId;

    @JsonProperty("grade")
    private Double rating;

    public Rating(ObjectId id, String createdAt, String userId, ObjectId pitchId, Double rating) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.pitchId = pitchId;
        this.rating = rating;
    }

    public Rating(ObjectId id, String userId, ObjectId pitchId, Double rating) {
        this.id = id;
        this.userId = userId;
        this.pitchId = pitchId;
        this.rating = rating;
    }

    public Rating() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ObjectId getPitchId() {
        return pitchId;
    }

    public void setPitchId(ObjectId pitchId) {
        this.pitchId = pitchId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
