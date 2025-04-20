package io.github.kennethfan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class TypeMerger {
    public static JsonNode mergeType(JsonNode type1, JsonNode type2) {
        if (Objects.equals(type1, type2)) {
            return type1;
        }

        Set<JsonNode> types = new HashSet<>();
        merge(types, type1);
        merge(types, type2);

        ArrayNode type = SchemaUtil.arrayNode();
        type.addAll(types);

        return type;
    }

    private static void merge(Set<JsonNode> exists, JsonNode type) {
        if (type instanceof ArrayNode) {
            Iterator<JsonNode> iterator = type.elements();
            while (iterator.hasNext()) {
                exists.add(iterator.next());
            }

            return;
        }

        exists.add(type);
    }
}
