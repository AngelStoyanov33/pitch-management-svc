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
@JsonPropertyOrder({"id", "name", "type", "borders_geo_data", "roles_required"})
@MongoEntity(collection = "Pitch", database = "sporton-dev-db")
public class Pitch {
    @JsonProperty("id")
    public ObjectId id; // used by MongoDB for the _id field

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "type", required = true)
    private PitchType type;

    @JsonProperty(value = "borders_geo_data", required = true)
    @BsonProperty("borders_geo_data")
    private List<GeoPoint> bordersGeoData;

    @JsonProperty(value = "wayId", required = true)
    @BsonProperty("wayId")
    private long wayId;

    @JsonProperty(value = "tags")
    @BsonProperty("tags")
    private Map<String, String> tags;

    @JsonProperty(value = "roles_required")
    @BsonProperty("roles_required")
    //TODO: Complex role object
    private List<String> rolesRequired = null;

    public Pitch(ObjectId id, String name, PitchType type, List<GeoPoint> bordersGeoData, long wayId, Map<String, String> tags, List<String> rolesRequired) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.bordersGeoData = bordersGeoData;
        this.wayId = wayId;
        this.tags = tags;
        this.rolesRequired = rolesRequired;
    }

    public Pitch(String name, PitchType type, long wayId, List<GeoPoint> bordersGeoData) {
        this.name = name;
        this.type = type;
        this.wayId = wayId;
        this.bordersGeoData = bordersGeoData;
    }

    public Pitch() {
    }

    public Pitch(Pitch pitch) {
        this.name = pitch.name;
        this.type = pitch.type;
        this.bordersGeoData = pitch.bordersGeoData;
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

    public List<GeoPoint> getBordersGeoData() {
        return bordersGeoData;
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

    public void addGeoPoint(GeoPoint geoPoint) {
        this.bordersGeoData.add(geoPoint);
    }

    public void removeGeoPoint(GeoPoint geoPoint) {
        this.bordersGeoData.remove(geoPoint);
    }

    public void setBordersGeoData(List<GeoPoint> bordersGeoData) {
        this.bordersGeoData = bordersGeoData;
    }

    public List<String> getRolesRequired() {
        return rolesRequired;
    }

    public void setRolesRequired(List<String> rolesRequired) {
        this.rolesRequired = rolesRequired;
    }
}
