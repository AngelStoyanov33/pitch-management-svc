package me.angelstoyanov.sporton.management.pitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@JsonRootName("pitch")
@JsonPropertyOrder({"id", "name", "type", "borders_geo_data", "roles_required"})
@MongoEntity(collection = "Pitch", database = "sporton-test-db")
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

    @JsonProperty(value = "roles_required")
    @BsonProperty("roles_required")
    //TODO: Complex role object
    private List<String> rolesRequired = null;
}
