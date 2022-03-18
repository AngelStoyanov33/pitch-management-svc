package me.angelstoyanov.sporton.management.pitch.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.runtime.annotations.RegisterForReflection;
import me.angelstoyanov.sporton.management.pitch.exception.PitchAlreadyExistsException;
import me.angelstoyanov.sporton.management.pitch.exception.PitchNotExistsException;
import me.angelstoyanov.sporton.management.pitch.model.GeoPoint;
import me.angelstoyanov.sporton.management.pitch.model.Pitch;
import me.angelstoyanov.sporton.management.pitch.model.PitchType;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.Locale;

@Named("PitchRepository")
@ApplicationScoped
@RegisterForReflection
public class PitchRepository implements PanacheMongoRepository<Pitch> {

    public List<Pitch> findByType(PitchType pitchType) {
        return list("type", pitchType);
    }
    public List<Pitch> findPitchesByRegion(String region) {
        return list("region", region);
    }
    public List<Pitch> findPitchesByRegionAndType(String region, PitchType type) {
        return list(String.format(Locale.US,"{\"region\":\"%s\",\"type\":\"%s\"}", region, type));
    }

    public Pitch findByPitchId(ObjectId id) {
        Pitch pitch = findById(id);
        if(pitch == null) {
            throw new PitchNotExistsException("Pitch with id " + id + " does not exist");
        }
        return pitch;
    }

    public Pitch addPitch(Pitch pitch) throws PitchAlreadyExistsException {
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
        Pitch pitchToUpdate = new Pitch(id, pitch);
        persistOrUpdate(pitchToUpdate);
        return pitchToUpdate;
    }

    public List<Pitch> findPitchesNearMe(double lat, double lon, double radius, PitchType type) {
        return list(String.format(Locale.US,"{ \"location.coordinates\" : { \"$near\" : { \"$geometry\" : { \"type\" : \"Point\", \"coordinates\" : [%.8f, %.8f] }, $maxDistance: %.8f } }, \"type\" : \"%s\" } ", lat, lon, radius, type));
    }
    public List<Pitch> findPitchesNearMe(double lat, double lon, double radius) {
        return list(String.format(Locale.US,"{ \"location.coordinates\" : { \"$near\" : { \"$geometry\" : { \"type\" : \"Point\", \"coordinates\" : [%.8f, %.8f] }, $maxDistance: %.8f } }} ", lat, lon, radius));
    }
}
