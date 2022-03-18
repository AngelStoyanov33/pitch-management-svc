package me.angelstoyanov.sporton.management.pitch.model.comment;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonRootName("comment")
@JsonPropertyOrder({"id", "created_at", "user_id", "pitch_id", "content", "attachment_uri"})
@RegisterForReflection
public class Comment {
    @JsonProperty("id")
    private ObjectId id;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("pitch_id")
    private ObjectId pitchId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("attachment_uri")
    private String attachment;


    public Comment(ObjectId id, String createdAt, String userId, ObjectId pitchId, String content, String attachment) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.pitchId = pitchId;
        this.content = content;
        this.attachment = attachment;
    }

    public Comment(String userId, ObjectId pitchId, String content, String attachment) {
        this.userId = userId;
        this.pitchId = pitchId;
        this.content = content;
        this.attachment = attachment;
    }

    public Comment() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ObjectId getPitchId() {
        return pitchId;
    }

    public void setPitchId(ObjectId pitchId) {
        this.pitchId = pitchId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}

