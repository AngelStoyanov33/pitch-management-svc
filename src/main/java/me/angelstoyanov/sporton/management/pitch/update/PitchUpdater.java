package me.angelstoyanov.sporton.management.pitch.update;

import io.quarkus.runtime.annotations.RegisterForReflection;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import me.angelstoyanov.sporton.management.pitch.osm.adapter.OSMAPIAdapter;
import me.angelstoyanov.sporton.management.pitch.repository.PitchRepository;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@RegisterForReflection
@ApplicationScoped
public class PitchUpdater {

    @Inject
    ManagedExecutor exec;

    @Inject
    protected OSMAPIAdapter osmapiAdapter;

    @Inject
    protected PitchRepository pitchRepository;

    public void updatePitches() {
        PitchType[] pitchTypes = PitchType.values();
        for (PitchType pitchType : pitchTypes) {
            try {
                exec.execute(() -> {
                    Set<Pitch> pitches = osmapiAdapter.fetchPitches(pitchType);
                    for (Pitch pitch : pitches) {
                        Pitch dbPitch = pitchRepository.findByWayId(pitch.getWayId());
                        if (dbPitch == null) {
                            pitchRepository.addPitch(pitch);
                        } else {
                            if (dbPitch.getVersion() != pitch.getVersion()) {
                                pitchRepository.addPitch(pitch);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
