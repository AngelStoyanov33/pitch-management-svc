package me.angelstoyanov.sporton.management.pitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonRootName("pitch")
@JsonPropertyOrder({"id", "name", "type", "location", "way_id", "tags", "attachment_uri", "roles_required"})
@MongoEntity(collection = "Pitch", database = "sporton-dev-db")
public class Pitch {
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
    @BsonProperty("way_id")
    private long wayId;

    @JsonProperty(value = "tags")
    @BsonProperty("tags")
    private Map<String, String> tags;

    @JsonProperty("region_id")
    @BsonProperty("region_id")
    private ObjectId regionId;

    @JsonProperty("attachment_uri")
    @BsonProperty("attachment_uri")
    private String attachment;

    @JsonProperty(value = "roles_required")
    @BsonProperty("roles_required")
    //TODO: Complex role object
    private List<String> rolesRequired = null;

    public Pitch(ObjectId id, String name, PitchType type, Polygon location, long wayId, ObjectId regionId, Map<String, String> tags, List<String> rolesRequired) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.wayId = wayId;
        this.tags = tags;
        this.regionId = regionId;
        this.rolesRequired = rolesRequired;
    }

    public Pitch(String name, PitchType type, long wayId, ObjectId regionId, Polygon location) {
        this.name = name;
        this.type = type;
        this.wayId = wayId;
        this.regionId = regionId;
        this.location = location;
    }

    public Pitch() {
    }

    public Pitch(ObjectId id, Pitch pitch) {
        this.id = id;
        this.name = pitch.name;
        this.type = pitch.type;
        this.location = pitch.location;
        this.wayId = pitch.wayId;
        this.tags = pitch.tags;
        this.regionId = pitch.regionId;
        this.attachment = pitch.attachment;
        this.rolesRequired = pitch.rolesRequired;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public List<String> getRolesRequired() {
        return rolesRequired;
    }

    public void setRolesRequired(List<String> rolesRequired) {
        this.rolesRequired = rolesRequired;
    }
}
