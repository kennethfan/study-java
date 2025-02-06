package io.github.kennethfan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SchemaMerge {

    public static JsonNode merge(ObjectNode schema) {
        if (schema.properties().size() <= 2) {
            return schema;
        }
        List<JsonNode> subSchemas = schema.get(SchemaConstants.Keys.PROPERTIES)
                .properties()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        JsonNode propertySchema = null;
        for (JsonNode subSchema : subSchemas) {
            if (propertySchema == null) {
                propertySchema = subSchema;
                continue;
            }
            propertySchema = mergeSchema(propertySchema, subSchema);
        }

        schema.remove(SchemaConstants.Keys.PROPERTIES);
        schema.remove(SchemaConstants.Keys.REQUIRED);
        schema.replace(SchemaConstants.Keys.ADDITIONAL_PROPERTIES, propertySchema);

        return schema;
    }

    private static JsonNode mergeSchema(JsonNode schema1, JsonNode schema2) {
        JsonNode type1 = schema1.get(SchemaConstants.Keys.TYPE);
        JsonNode type2 = schema1.get(SchemaConstants.Keys.TYPE);
        if (SchemaUtil.isPrimitive(schema1) && SchemaUtil.isPrimitive(schema2)) {
            return mergeType(type1, type2);
        }

        if (SchemaUtil.isMap(schema1)) {
//            type1 = merge
        }

        return null;
    }

    public static JsonNode mergeMap(JsonNode schema1, JsonNode schema2) {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode result = factory.objectNode();
        Map<String, JsonNode> properties1 = SchemaUtil.getProperties(schema1);
        Map<String, JsonNode> properties2 = SchemaUtil.getProperties(schema2);
        for (Map.Entry<String, JsonNode> entry : properties1.entrySet()) {
            String key = entry.getKey();
            JsonNode type = entry.getValue().get(SchemaConstants.Keys.TYPE);
            if (SchemaConstants.Types.TYPE_OBJECT.equals(type.asText())) {
                type = merge((ObjectNode) entry.getValue());
            }

            if (!properties2.containsKey(key)) {
                type = mergeType(type, nullType());
            }
            JsonNode value2= properties2.get(key);
            JsonNode type2 = value2.get(SchemaConstants.Keys.TYPE);
            if (SchemaConstants.Types.TYPE_OBJECT.equals(type2.asText())) {
                type2 = merge((ObjectNode) value2);
            }
            type  = mergeType(type, type2);
            ObjectNode objectNode = objectNode();
            objectNode.put(SchemaConstants.Keys.TYPE, type);
            result.put(key, objectNode);
        }

        return result;
    }

    private static ObjectNode objectNode() {
        return JsonNodeFactory.instance.objectNode();

    }

    private static ArrayNode arrayNode() {
        return JsonNodeFactory.instance.arrayNode();

    }

    private static TextNode nullType() {
        return JsonNodeFactory.instance.textNode("null");
    }

    private static JsonNode mergeType(JsonNode type1, JsonNode type2) {
        if (type1.equals(type2)) {
            return type1;
        }

        if (type1.isArray()) {
            ArrayNode result = (ArrayNode) type1;
            Set<JsonNode> types = toSet(result);
            if (types.contains(type2)) {
                return result;
            }
        }

        ArrayNode result = arrayNode();
        result.add(type1);
        result.add(type2);

        return result;
    }

    public static Set<JsonNode> toSet(ArrayNode arrayNode) {
        Set<JsonNode> result = new HashSet<>();
        arrayNode.elements().forEachRemaining(result::add);
        return result;
    }


    public static void main(String[] args) {
        ArrayNode node = arrayNode();
        node.add(1);
        node.add("1");
        node.add(true);
        node.add(objectNode());
        node.add(arrayNode());
//        node.add(new NullNode());
        ObjectNode objectNode = objectNode();

        System.out.println(node);
    }
}
