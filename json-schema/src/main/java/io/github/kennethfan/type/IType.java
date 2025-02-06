package io.github.kennethfan.type;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public interface IType {

    JsonNodeType getType();

    JsonNode toNode();
}
