package io.github.kennethfan;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
public class SchemaUtil {

    public static final JsonNode PATTERN_PROPERTIES = patternProperties();

    public static boolean isObject(JsonNode node) {
        if (node == null) {
            return false;
        }

        JsonNode type = node.get(SchemaConstants.Keys.TYPE);
        return type != null && SchemaConstants.Types.TYPE_OBJECT.equals(type.asText());
    }

    public static boolean isMap(JsonNode node) {
        if (!isObject(node)) {
            return false;
        }

        Iterator<String> fields = node.fieldNames();
        while (fields.hasNext()) {
            String key = fields.next();
            if (!StrUtil.isNumeric(key)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isArray(JsonNode node) {
        if (node == null) {
            return false;
        }

        JsonNode type = node.get(SchemaConstants.Keys.TYPE);
        return type != null && SchemaConstants.Types.TYPE_ARRAY.equals(type.asText());
    }

    public static boolean isPrimitive(JsonNode node) {
        return node != null && !isObject(node) && !isArray(node);
    }

    public static Map<String, JsonNode> getProperties(JsonNode node) {
        log.info("getProperties: {}", node);
        return Optional.ofNullable(node.get(SchemaConstants.Keys.PROPERTIES))
                .map(JsonNode::properties)
                .orElse(new HashSet<>())
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public static ObjectNode objectNode() {
        return JsonNodeFactory.instance.objectNode();

    }

    public static ArrayNode arrayNode() {
        return JsonNodeFactory.instance.arrayNode();

    }

    public static NullNode nullType() {
        return JsonNodeFactory.instance.nullNode();
    }

    private static JsonNode numberType() {
        ObjectNode node = objectNode();
        node.put(SchemaConstants.Keys.TYPE, SchemaConstants.Types.TYPE_NUMBER);
        return node;
    }

    private static JsonNode patternProperties() {
        ObjectNode node = objectNode();
        node.put("^[0-9]+$", numberType());
        return node;
    }
}
