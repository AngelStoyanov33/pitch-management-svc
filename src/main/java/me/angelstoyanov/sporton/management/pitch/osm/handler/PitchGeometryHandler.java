package me.angelstoyanov.sporton.management.pitch.osm.handler;

import de.westnordost.osmapi.map.data.*;
import de.westnordost.osmapi.overpass.MapDataWithGeometryHandler;
import me.angelstoyanov.sporton.management.pitch.model.GeoPoint;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.model.Polygon;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PitchGeometryHandler implements MapDataWithGeometryHandler {

    private final HashSet<Pitch> fetchedPitches;
    private final PitchType pitchType;

    public PitchGeometryHandler(PitchType pitchType) {
        this.pitchType = pitchType;
        fetchedPitches = new HashSet<>();
    }

    @Override
    public void handle(@NotNull BoundingBox boundingBox) {

    }

    @Override
    public void handle(@NotNull Node node) {

    }

    @Override
    public void handle(@NotNull Way way, @NotNull BoundingBox boundingBox, @NotNull List<LatLon> list) {
        try {
            Pitch pitch = new Pitch();
            pitch.setTags(way.getTags());
            pitch.setName(way.getTags().getOrDefault("name", "Unknown"));
            pitch.setWayId(way.getId());
            pitch.setType(pitchType);
            pitch.setLocation(new Polygon(list, way.getNodeIds()));

            this.fetchedPitches.add(pitch);
        } catch (Exception e) {
            System.out.println("Error while handling way: " + way.getId());
        }
    }

    @Override
    public void handle(@NotNull Relation relation, @NotNull BoundingBox boundingBox, @NotNull Map<Long, LatLon> map, @NotNull Map<Long, List<LatLon>> map1) {

    }

    public HashSet<Pitch> getFetchedPitches() {
        return fetchedPitches;
    }
}
