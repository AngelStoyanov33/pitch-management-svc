package me.angelstoyanov.sporton.management.pitch.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import me.angelstoyanov.sporton.management.pitch.exception.PitchAlreadyExistsException;
import me.angelstoyanov.sporton.management.pitch.exception.PitchNotExistsException;
import me.angelstoyanov.sporton.management.pitch.model.GeoPoint;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named("PitchRepository")
@ApplicationScoped
public class PitchRepository implements PanacheMongoRepository<Pitch> {

    public List<Pitch> findByType(PitchType pitchType) {
        return list("type", pitchType);
    }
    public Pitch findByLocation(List<GeoPoint> location) {
        return find("borders_geo_data", location).firstResult();
    }
    public Pitch findByName(String name) {
        return find("name", name).firstResult();
    }

    public Pitch addPitch(Pitch pitch) throws PitchAlreadyExistsException {
        if (findByName(pitch.getName()) != null) {
            throw new PitchAlreadyExistsException("Pitch with name " + pitch.getName() + " already exists");
        }
        persist(pitch);
        return pitch;
    }

    public void deletePitch(Pitch pitch) throws PitchNotExistsException {
        if (findById(pitch.getId()) == null) {
            throw new PitchNotExistsException("Pitch with id " + pitch.getId() + " does not exist");
        }
        delete(pitch);
    }

    public void deletePitchById(ObjectId id) throws PitchNotExistsException {
        if (findById(id) == null) {
            throw new PitchNotExistsException("Pitch with id " + id + " does not exist");
        }
        deleteById(id);
    }

    public Pitch replacePitch(ObjectId id, Pitch pitch) throws PitchNotExistsException {
        if (findById(id) == null) {
            throw new PitchNotExistsException("Pitch with id " + pitch.getId() + " does not exist");
        }
        Pitch pitchToUpdate = new Pitch(pitch);
        pitchToUpdate.setId(id);
        persistOrUpdate(pitchToUpdate);
        return pitchToUpdate;
    }


}
