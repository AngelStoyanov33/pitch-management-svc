package me.angelstoyanov.sporton.management.pitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.westnordost.osmapi.map.data.LatLon;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.LinkedList;
import java.util.List;

@JsonRootName("location")
@JsonPropertyOrder({"type", "coordinates"})
public class Polygon {

    @JsonProperty(value = "type", required = true, defaultValue = "Polygon")
    @BsonProperty("type")
    private String type = "Polygon";

    @JsonProperty(value = "coordinates", required = true)
    @BsonProperty("coordinates")
    private List<List<Double>> coordinates;

    @JsonProperty(value = "node_ids", required = false)
    @BsonProperty("node_ids")
    private List<Long> nodeIds;

    public Polygon() {
    }

    public Polygon(List<LatLon> points) {
        this.coordinates = new LinkedList<>();
        points.forEach(point -> this.coordinates.add(new LinkedList<>(List.of(point.getLatitude(), point.getLongitude()))));
    }

    public Polygon(List<LatLon> points, List<Long> nodeIs) {
        this.coordinates = new LinkedList<>();
        this.nodeIds = nodeIs;
        points.forEach(point -> this.coordinates.add(new LinkedList<>(List.of(point.getLatitude(), point.getLongitude()))));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }
}
