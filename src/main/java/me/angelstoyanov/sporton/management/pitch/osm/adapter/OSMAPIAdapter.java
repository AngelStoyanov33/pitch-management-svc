package me.angelstoyanov.sporton.management.pitch.osm.adapter;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
import me.angelstoyanov.sporton.management.pitch.config.OSMAPIConfig;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.osm.handler.PitchGeometryHandler;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;


@ApplicationScoped
@Named("OSMAPIAdapter")
public class OSMAPIAdapter {

    private Logger logger = Logger.getLogger(OSMAPIAdapter.class);

    @Inject
    protected OSMAPIConfig config;

    private OsmConnection connection;
    private OverpassMapDataApi overpassApi;

    private String getActiveServerInstance() {
        if (config.getActiveServerIndex() < 0 || config.getActiveServerIndex() >= config.getActiveServersList().size()) {
            throw new IllegalArgumentException("Invalid active server index");
        }
        return config.getActiveServersList().get(config.getActiveServerIndex());
    }

    public HashSet<Pitch> fetchPitches(PitchType pitchType) {
        connection = new OsmConnection(getActiveServerInstance(), config.getUserAgent());
        overpassApi = new OverpassMapDataApi(connection);

        logger.debugv("Request to OSM API: " + buildQuery(pitchType));

        PitchGeometryHandler handler = new PitchGeometryHandler(pitchType);
        overpassApi.queryElementsWithGeometry(buildQuery(pitchType), handler);
        return handler.getFetchedPitches();
    }

    private String buildQuery(PitchType pitchType) {
        StringBuilder querySb = new StringBuilder();
        querySb.append("[timeout:").append(config.getRequestTimeout()).append("];");
        querySb.append("area[\"name:en\"=\"Sofia\"][place=\"city\"];");
        querySb.append("nwr(area)[\"leisure\"=\"pitch\"][\"sport\"=\"").append(pitchType.getOsmName()).append("\"];");
        querySb.append("out meta geom;");
        return querySb.toString();
    }

}
