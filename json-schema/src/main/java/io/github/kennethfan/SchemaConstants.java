package io.github.kennethfan;

public interface SchemaConstants {

    interface Keys {

        String SCHEMA = "schema";

        String TYPE = "type";

        String PROPERTIES = "properties";

        String REQUIRED = "required";

        String ADDITIONAL_PROPERTIES = "additionalProperties";

        String PATTERN_PROPERTIES = "patternProperties";


        String ITEMS = "items";
    }

    interface Types {

        String TYPE_OBJECT = "object";

        String TYPE_ARRAY = "array";

        String TYPE_NUMBER = "number";

        String Type_ANY_OF = "anyOf";
    }
}
