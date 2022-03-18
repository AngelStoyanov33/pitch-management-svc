package me.angelstoyanov.sporton.management.pitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.westnordost.osmapi.map.data.LatLon;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@RegisterForReflection
@JsonRootName("point")
public class GeoPoint {

    @JsonProperty(value = "lat", required = true)
    @BsonProperty("lat")
    private double latitude;

    @JsonProperty(value = "lon", required = true)
    @BsonProperty("lon")
    private double longitude;

    @JsonProperty(value = "node_id", required = false)
    @BsonProperty("node_id")
    private long nodeId;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint(double latitude, double longitude, long nodeId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nodeId = nodeId;
    }

    public GeoPoint(){

    }

    public GeoPoint(LatLon latLon, long nodeId) {
        this.latitude = latLon.getLatitude();
        this.longitude = latLon.getLongitude();
        this.nodeId = nodeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint geoPoint = (GeoPoint) o;
        return Double.compare(geoPoint.latitude, latitude) == 0 && Double.compare(geoPoint.longitude, longitude) == 0 && Double.compare(geoPoint.nodeId, nodeId) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, nodeId);
    }

    @Override
    public String toString() {
        return "{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", node_id=" + nodeId +
                '}';
    }
}
