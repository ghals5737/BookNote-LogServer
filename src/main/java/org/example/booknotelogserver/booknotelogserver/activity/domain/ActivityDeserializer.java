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
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.memo.MemoUpdateTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserTarget;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserUpdateTarget;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ActivityDeserializer extends JsonDeserializer<Activity> {

    private final ObjectMapper objectMapper;

    // ObjectMapper를 주입받도록 설정 (Spring이 관리하는 ObjectMapper)
    public ActivityDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Activity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 전체 JSON 트리
        JsonNode rootNode = p.getCodec().readTree(p);

        // 각 필드 추출
        String id = rootNode.get("id").asText();
        String action = rootNode.get("action").asText();
        JsonNode actorNode = rootNode.get("actor");
        JsonNode targetNode = rootNode.get("target");
        String timestamp = rootNode.get("timestamp").asText();

        // Actor는 간단히 처리 가능하다고 가정
        Actor actor = objectMapper.readValue(actorNode.toString(), Actor.class);

        // Target 필드 처리: action에 따라 구체적인 타입으로 변환
        Target target = determineTargetType(action, targetNode);

        return new Activity(id, action, actor, target, timestamp);
    }

    // action에 따라 구체적인 Target 클래스를 결정하는 로직
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
