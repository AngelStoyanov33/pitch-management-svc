package me.angelstoyanov.sporton.management.pitch.model.extended;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.model.Polygon;
import me.angelstoyanov.sporton.management.pitch.model.comment.Comment;
import me.angelstoyanov.sporton.management.pitch.model.rating.Rating;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@JsonRootName("pitch")
@JsonPropertyOrder({"id", "name", "type", "location", "way_id", "tags", "attachment_uri", "roles_required", "comments", "ratings", "average_rating", "my_rating"})
public class ExtendedPitch {
    @JsonProperty("id")
    public ObjectId id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "type", required = true)
    private PitchType type;

    @JsonProperty(value = "location", required = true)
    @BsonProperty("location")
    private Polygon location;

    @JsonProperty(value = "way_id", required = true)
    private long wayId;

    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    @JsonProperty("region_id")
    private ObjectId regionId;

    @JsonProperty("attachment_uri")
    @BsonProperty("attachment_uri")
    private String attachment;

    @JsonProperty(value = "roles_required")
    @BsonProperty("roles_required")
    //TODO: Complex role object
    private List<String> rolesRequired = null;

    @JsonProperty(value = "comments")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> comments = null;

    @JsonProperty(value = "ratings")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Rating> ratings = null;

    @JsonProperty(value = "average_rating")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double averageRating = null;

    @JsonProperty(value = "my_rating")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Rating myRating = null;

    public ExtendedPitch(ObjectId id, String name, PitchType type, Polygon location, long wayId, ObjectId regionId, Map<String, String> tags, List<String> rolesRequired) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.wayId = wayId;
        this.tags = tags;
        this.regionId = regionId;
        this.rolesRequired = rolesRequired;
    }

    public ExtendedPitch(String name, PitchType type, long wayId, ObjectId regionId, Polygon location) {
        this.name = name;
        this.type = type;
        this.wayId = wayId;
        this.regionId = regionId;
        this.location = location;
    }

    public ExtendedPitch() {
    }

    public ExtendedPitch(Pitch pitch) {
        this.name = pitch.getName();
        this.type = pitch.getType();
        this.location = pitch.getLocation();
        this.rolesRequired = pitch.getRolesRequired();
        this.regionId = pitch.getRegionId();
        this.tags = pitch.getTags();
        this.wayId = pitch.getWayId();
        this.id = pitch.getId();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PitchType getType() {
        return type;
    }

    public void setType(PitchType type) {
        this.type = type;
    }

    public Polygon getLocation() {
        return location;
    }

    public long getWayId() {
        return wayId;
    }

    public void setWayId(long wayId) {
        this.wayId = wayId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void setLocation(Polygon location) {
        this.location = location;
    }

    public ObjectId getRegionId() {
        return regionId;
    }

    public void setRegionId(ObjectId regionId) {
        this.regionId = regionId;
    }

    public List<String> getRolesRequired() {
        return rolesRequired;
    }

    public void setRolesRequired(List<String> rolesRequired) {
        this.rolesRequired = rolesRequired;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Rating getMyRating() {
        return myRating;
    }

    public void setMyRating(Rating myRating) {
        this.myRating = myRating;
    }
}
