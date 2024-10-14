package org.example.booknotelogserver.booknotelogserver.activity.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.book.BookTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.book.BookUpdateTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.memo.MemoTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserUpdateTarget;

import java.io.IOException;


public class ActivityDeserializer extends JsonDeserializer<Activity> {

    private final ObjectMapper objectMapper;

    public ActivityDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Activity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);


        String id = rootNode.get("id").asText();
        String action = rootNode.get("action").asText();
        JsonNode actorNode = rootNode.get("actor");
        JsonNode targetNode = rootNode.get("target");
        String timestamp = rootNode.get("timestamp").asText();

        Actor actor = objectMapper.readValue(actorNode.toString(), Actor.class);

        Target target = determineTargetType(action, targetNode);

        return new Activity(id, action, actor, target, timestamp);
    }


    private Target determineTargetType(String action, JsonNode targetNode) throws IOException {
        return switch (action) {
            case "book.select", "book.delete", "book.create", "memo.update" -> objectMapper.readValue(targetNode.toString(), new TypeReference<BookTarget>() {});
            case "book.update" -> objectMapper.readValue(targetNode.toString(), new TypeReference<BookUpdateTarget>() {});
            case "memo.select", "memo.delete", "memo.create" -> objectMapper.readValue(targetNode.toString(), new TypeReference<MemoTarget>() {});
            case "user.create", "user.select" -> objectMapper.readValue(targetNode.toString(), new TypeReference<UserTarget>() {});
            case "user.update" -> objectMapper.readValue(targetNode.toString(), new TypeReference<UserUpdateTarget>() {});
            default -> throw new RuntimeException("Unknown action type: " + action);
        };
    }
}
