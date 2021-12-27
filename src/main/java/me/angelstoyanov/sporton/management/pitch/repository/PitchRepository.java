package me.angelstoyanov.sporton.management.pitch.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named("PitchRepository")
@ApplicationScoped
public class PitchRepository implements PanacheMongoRepository<Pitch> {

    public List<Pitch> findByType(PitchType pitchType) {
        return list("type", pitchType);
    }
    
}
