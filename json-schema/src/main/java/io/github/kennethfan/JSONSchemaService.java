package io.github.kennethfan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.saasquatch.jsonschemainferrer.*;
import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JSONSchemaService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final JsonSchemaInferrer INFERRER = JsonSchemaInferrer.newBuilder()
            .setSpecVersion(SpecVersion.DRAFT_06)
            .addFormatInferrers(FormatInferrers.email(), FormatInferrers.ip())
            .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.notAllowed())
            .setRequiredPolicy(RequiredPolicies.nonNullCommonFields())
            .addEnumExtractors(EnumExtractors.validEnum(java.time.Month.class),
                    EnumExtractors.validEnum(java.time.DayOfWeek.class))
            .build();

    private static final LoadingCache<String, Schema> SCHEMA_CACHE = CacheBuilder.newBuilder()
            .softValues()
            .weakKeys()
            .maximumSize(10000L)
            .expireAfterAccess(Duration.ofMinutes(5))
            .build(new CacheLoader<String, Schema>() {
                @Override
                public Schema load(String json) {
                    return SchemaLoader.load(new JSONObject(new JSONTokener(json)));
                }
            });

    /**
     * 根据json构建schema
     *
     * @param json
     * @return
     */
    public String buildSchema(String json) {
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
            ObjectNode schemaNode = INFERRER.inferForSample(jsonNode);
            if (SchemaUtil.isMap(jsonNode)) {
                return OBJECT_MAPPER.writeValueAsString(SchemaMerger.merge(schemaNode));
            }
            return OBJECT_MAPPER.writeValueAsString(schemaNode);
        } catch (Exception e) {
            log.error("Failed to build schema, json", json, e);
        }
        return null;
    }

    private Object convert(String json) throws JsonProcessingException {
        JsonNode node = OBJECT_MAPPER.readTree(json);
        if (node == null) {
            return JSONObject.NULL;
        }

        if (node.isObject()) {
            return new JSONObject(new JSONTokener(json));
        }

        if (node.isArray()) {
            return new JSONArray(new JSONTokener(json));
        }

        if (node.isNull()) {
            return JSONObject.NULL;
        }

        if (node.isTextual()) {
            return node.asText();
        }

        if (node.isBoolean()) {
            return Boolean.valueOf(json);
        }

        if (node.isIntegralNumber()) {
            return Long.parseLong(json);
        }

        if (node.isFloatingPointNumber()) {
            return Double.parseDouble(json);
        }

        return node.asText();
    }

    /**
     * 检测json是否符合schema定义
     *
     * @param schemaJson
     * @param json       需要检测的json
     * @return
     */
    public List<String> checkWithSchema(String schemaJson, String json) {
        try {
            Schema schema = SCHEMA_CACHE.get(schemaJson);
            schema.validate(convert(json));
        } catch (ValidationException e) {
            log.error("checkWithSchema error", e);
            return e.getAllMessages();
        } catch (Exception e) {
            log.error("checkWithSchema error", e);
        }

        return Collections.emptyList();
    }

    public static void main(String[] args) throws JsonProcessingException {
        JSONSchemaService service = new JSONSchemaService();
        // String json = "{\"cookie\":\"sess=63246f1053446dd5446d62b1\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36\",\"xbc\":\"7d4714576903fce36429b7459e169250fff64d78\"}";
        // String schema = service.buildSchema(json);
        // System.out.println(schema);
        // System.out.println(service.checkWithSchema(schema, "{}"));
        // System.out.println(service.checkWithSchema(schema, "{\"xbc\":\"222\"}"));
        //
        // json = "124";
        // System.out.println(service.buildSchema(json));
        //
        // json = "true";
        // System.out.println(service.buildSchema(json));
        //
        // json = "\"1344\"";
        // System.out.println(service.buildSchema(json));
        //
        // json = "[ \"home\", \"green\" ]";
        // System.out.println(service.buildSchema(json));

        String json = "{\n"
                + "    \"112404466\": {\n"
                + "        \"view\": \"r\",\n"
                + "        \"avatar\": \"https:\\/\\/public.onlyfans.com\\/files\\/s\\/se\\/se1\\/se1krrrk2mmayoux2o0jlprpouennrjb1697041023\\/112404466\\/avatar.jpg\",\n"
                + "        \"avatarThumbs\": {\n"
                + "            \"c50\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c50\\/s\\/se\\/se1\\/se1krrrk2mmayoux2o0jlprpouennrjb1697041023\\/112404466\\/avatar.jpg\",\n"
                + "            \"c144\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c144\\/s\\/se\\/se1\\/se1krrrk2mmayoux2o0jlprpouennrjb1697041023\\/112404466\\/avatar.jpg\"\n"
                + "        },\n"
                + "        \"header\": \"https:\\/\\/public.onlyfans.com\\/files\\/j\\/jc\\/jcb\\/jcblaccetlry6xxhfbqnkdx8tl1sewsl1659721892\\/112404466\\/header.jpg\",\n"
                + "        \"headerSize\": {\n"
                + "            \"width\": 3628,\n"
                + "            \"height\": 2419\n"
                + "        },\n"
                + "        \"headerThumbs\": {\n"
                + "            \"w480\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w480\\/j\\/jc\\/jcb\\/jcblaccetlry6xxhfbqnkdx8tl1sewsl1659721892\\/112404466\\/header.jpg\",\n"
                + "            \"w760\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w760\\/j\\/jc\\/jcb\\/jcblaccetlry6xxhfbqnkdx8tl1sewsl1659721892\\/112404466\\/header.jpg\"\n"
                + "        },\n"
                + "        \"id\": 112404466,\n"
                + "        \"name\": \"Surfer Girl\",\n"
                + "        \"username\": \"surfwithclaire\",\n"
                + "        \"canLookStory\": false,\n"
                + "        \"canCommentStory\": false,\n"
                + "        \"hasNotViewedStory\": false,\n"
                + "        \"isVerified\": true,\n"
                + "        \"canPayInternal\": true,\n"
                + "        \"hasScheduledStream\": false,\n"
                + "        \"hasStream\": false,\n"
                + "        \"hasStories\": false,\n"
                + "        \"tipsEnabled\": false,\n"
                + "        \"tipsTextEnabled\": true,\n"
                + "        \"tipsMin\": 5,\n"
                + "        \"tipsMinInternal\": 1,\n"
                + "        \"tipsMax\": 200,\n"
                + "        \"canEarn\": true,\n"
                + "        \"canAddSubscriber\": true,\n"
                + "        \"subscribePrice\": 0,\n"
                + "        \"isPaywallRequired\": true,\n"
                + "        \"isRestricted\": false,\n"
                + "        \"canRestrict\": true,\n"
                + "        \"subscribedBy\": false,\n"
                + "        \"subscribedByExpire\": null,\n"
                + "        \"subscribedByExpireDate\": null,\n"
                + "        \"subscribedByAutoprolong\": null,\n"
                + "        \"subscribedIsExpiredNow\": null,\n"
                + "        \"currentSubscribePrice\": null,\n"
                + "        \"subscribedOn\": false,\n"
                + "        \"subscribedOnExpiredNow\": null,\n"
                + "        \"subscribedOnDuration\": null,\n"
                + "        \"hasPromotion\": false,\n"
                + "        \"isFree\": true\n"
                + "    },\n"
                + "    \"302685263\": {\n"
                + "        \"view\": \"r\",\n"
                + "        \"avatar\": \"https:\\/\\/public.onlyfans.com\\/files\\/o\\/ob\\/oba\\/obaik91d1tuiau1caudoqkozk4czich11695806386\\/302685263\\/avatar.jpg\",\n"
                + "        \"avatarThumbs\": {\n"
                + "            \"c50\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c50\\/o\\/ob\\/oba\\/obaik91d1tuiau1caudoqkozk4czich11695806386\\/302685263\\/avatar.jpg\",\n"
                + "            \"c144\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c144\\/o\\/ob\\/oba\\/obaik91d1tuiau1caudoqkozk4czich11695806386\\/302685263\\/avatar.jpg\"\n"
                + "        },\n"
                + "        \"header\": \"https:\\/\\/public.onlyfans.com\\/files\\/r\\/r7\\/r7d\\/r7dzprkbs35tu5pipbempdzu57f79rx41695806387\\/302685263\\/header.jpg\",\n"
                + "        \"headerSize\": {\n"
                + "            \"width\": 2000,\n"
                + "            \"height\": 600\n"
                + "        },\n"
                + "        \"headerThumbs\": {\n"
                + "            \"w480\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w480\\/r\\/r7\\/r7d\\/r7dzprkbs35tu5pipbempdzu57f79rx41695806387\\/302685263\\/header.jpg\",\n"
                + "            \"w760\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w760\\/r\\/r7\\/r7d\\/r7dzprkbs35tu5pipbempdzu57f79rx41695806387\\/302685263\\/header.jpg\"\n"
                + "        },\n"
                + "        \"id\": 302685263,\n"
                + "        \"name\": \"Gracie Marie\",\n"
                + "        \"username\": \"graciemarieslife\",\n"
                + "        \"canLookStory\": false,\n"
                + "        \"canCommentStory\": false,\n"
                + "        \"hasNotViewedStory\": false,\n"
                + "        \"isVerified\": true,\n"
                + "        \"canPayInternal\": true,\n"
                + "        \"hasScheduledStream\": false,\n"
                + "        \"hasStream\": false,\n"
                + "        \"hasStories\": false,\n"
                + "        \"tipsEnabled\": false,\n"
                + "        \"tipsTextEnabled\": true,\n"
                + "        \"tipsMin\": 5,\n"
                + "        \"tipsMinInternal\": 1,\n"
                + "        \"tipsMax\": 5,\n"
                + "        \"canEarn\": true,\n"
                + "        \"canAddSubscriber\": true,\n"
                + "        \"subscribePrice\": 0,\n"
                + "        \"isPaywallRequired\": true,\n"
                + "        \"isRestricted\": false,\n"
                + "        \"canRestrict\": true,\n"
                + "        \"subscribedBy\": false,\n"
                + "        \"subscribedByExpire\": null,\n"
                + "        \"subscribedByExpireDate\": null,\n"
                + "        \"subscribedByAutoprolong\": null,\n"
                + "        \"subscribedIsExpiredNow\": null,\n"
                + "        \"currentSubscribePrice\": null,\n"
                + "        \"subscribedOn\": false,\n"
                + "        \"subscribedOnExpiredNow\": null,\n"
                + "        \"subscribedOnDuration\": null,\n"
                + "        \"hasPromotion\": false,\n"
                + "        \"isFree\": true\n"
                + "    },\n"
                + "    \"436296779\": {\n"
                + "        \"view\": \"r\",\n"
                + "        \"avatar\": \"https:\\/\\/public.onlyfans.com\\/files\\/r\\/rq\\/rqu\\/rqu87paylib2n8a3vu2l4xnxyyz5lsdb1723760570\\/436296779\\/avatar.jpg\",\n"
                + "        \"avatarThumbs\": {\n"
                + "            \"c50\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c50\\/r\\/rq\\/rqu\\/rqu87paylib2n8a3vu2l4xnxyyz5lsdb1723760570\\/436296779\\/avatar.jpg\",\n"
                + "            \"c144\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/c144\\/r\\/rq\\/rqu\\/rqu87paylib2n8a3vu2l4xnxyyz5lsdb1723760570\\/436296779\\/avatar.jpg\"\n"
                + "        },\n"
                + "        \"header\": \"https:\\/\\/public.onlyfans.com\\/files\\/n\\/ni\\/niq\\/niqm76n7gwueo1c4vms1tx7hkjdjg2e11723759138\\/436296779\\/header.jpg\",\n"
                + "        \"headerSize\": {\n"
                + "            \"width\": 1920,\n"
                + "            \"height\": 1080\n"
                + "        },\n"
                + "        \"headerThumbs\": {\n"
                + "            \"w480\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w480\\/n\\/ni\\/niq\\/niqm76n7gwueo1c4vms1tx7hkjdjg2e11723759138\\/436296779\\/header.jpg\",\n"
                + "            \"w760\": \"https:\\/\\/thumbs.onlyfans.com\\/public\\/files\\/thumbs\\/w760\\/n\\/ni\\/niq\\/niqm76n7gwueo1c4vms1tx7hkjdjg2e11723759138\\/436296779\\/header.jpg\"\n"
                + "        },\n"
                + "        \"id\": 436296779,\n"
                + "        \"name\": \"Jen\",\n"
                + "        \"username\": \"heyitsjenxx\",\n"
                + "        \"canLookStory\": false,\n"
                + "        \"canCommentStory\": false,\n"
                + "        \"hasNotViewedStory\": false,\n"
                + "        \"isVerified\": true,\n"
                + "        \"canPayInternal\": true,\n"
                + "        \"hasScheduledStream\": false,\n"
                + "        \"hasStream\": false,\n"
                + "        \"hasStories\": false,\n"
                + "        \"tipsEnabled\": false,\n"
                + "        \"tipsTextEnabled\": true,\n"
                + "        \"tipsMin\": 5,\n"
                + "        \"tipsMinInternal\": 1,\n"
                + "        \"tipsMax\": 200,\n"
                + "        \"canEarn\": true,\n"
                + "        \"canAddSubscriber\": true,\n"
                + "        \"subscribePrice\": 0,\n"
                + "        \"isPaywallRequired\": true,\n"
                + "        \"isRestricted\": false,\n"
                + "        \"canRestrict\": true,\n"
                + "        \"subscribedBy\": false,\n"
                + "        \"subscribedByExpire\": null,\n"
                + "        \"subscribedByExpireDate\": null,\n"
                + "        \"subscribedByAutoprolong\": null,\n"
                + "        \"subscribedIsExpiredNow\": null,\n"
                + "        \"currentSubscribePrice\": null,\n"
                + "        \"subscribedOn\": false,\n"
                + "        \"subscribedOnExpiredNow\": null,\n"
                + "        \"subscribedOnDuration\": null,\n"
                + "        \"hasPromotion\": false,\n"
                + "        \"isFree\": true\n"
                + "    }\n"
                + "}";

        System.out.println(json);


        String schema = "{\n"
                + "  \"$schema\": \"http://json-schema.org/draft-06/schema#\",\n"
                + "  \"type\": \"object\",\n"
                + "  \"additionalProperties\": {\n"
                + "    \"type\": \"object\",\n"
                + "    \"properties\": {\n"
                + "      \"avatar\": {\n"
                + "        \"type\": \"string\"\n"
                + "      },\n"
                + "      \"avatarThumbs\": {\n"
                + "        \"type\": \"object\",\n"
                + "        \"properties\": {\n"
                + "          \"c144\": {\n"
                + "            \"type\": \"string\"\n"
                + "          },\n"
                + "          \"c50\": {\n"
                + "            \"type\": \"string\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"additionalProperties\": false,\n"
                + "        \"required\": [\n"
                + "          \"c144\",\n"
                + "          \"c50\"\n"
                + "        ]\n"
                + "      },\n"
                + "      \"canCommentStory\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"canEarn\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"canLookStory\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"canPayInternal\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"canTrialSend\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"displayName\": {\n"
                + "        \"type\": \"string\"\n"
                + "      },\n"
                + "      \"hasNotViewedStory\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"hasStories\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"hasStream\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"id\": {\n"
                + "        \"type\": \"integer\"\n"
                + "      },\n"
                + "      \"isBlocked\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"isPerformer\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"isRealPerformer\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"isRestricted\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"isVerified\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"name\": {\n"
                + "        \"type\": \"string\"\n"
                + "      },\n"
                + "      \"subscribedOn\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"subscribedOnExpiredNow\": {\n"
                + "        \"type\": \"boolean\"\n"
                + "      },\n"
                + "      \"username\": {\n"
                + "        \"type\": \"string\"\n"
                + "      },\n"
                + "      \"view\": {\n"
                + "        \"type\": \"string\"\n"
                + "      }\n"
                + "    },\n"
                + "    \"additionalProperties\": false,\n"
                + "    \"required\": [\n"
                + "      \"avatar\",\n"
                + "      \"avatarThumbs\",\n"
                + "      \"canCommentStory\",\n"
                + "      \"canEarn\",\n"
                + "      \"canLookStory\",\n"
                + "      \"canPayInternal\",\n"
                + "      \"canTrialSend\",\n"
                + "      \"displayName\",\n"
                + "      \"hasNotViewedStory\",\n"
                + "      \"hasStories\",\n"
                + "      \"hasStream\",\n"
                + "      \"id\",\n"
                + "      \"isBlocked\",\n"
                + "      \"isPerformer\",\n"
                + "      \"isRealPerformer\",\n"
                + "      \"isRestricted\",\n"
                + "      \"isVerified\",\n"
                + "      \"name\",\n"
                + "      \"subscribedOn\",\n"
                + "      \"subscribedOnExpiredNow\",\n"
                + "      \"username\",\n"
                + "      \"view\"\n"
                + "    ]\n"
                + "  }\n"
                + "}\n";

        schema = service.buildSchema(json);
        System.out.println(schema);
        System.out.println(service.checkWithSchema(schema, json));

        System.out.println(service.buildSchema("1111111"));
        System.out.println(service.buildSchema("\"1111111\""));
        System.out.println(service.buildSchema("[1111, 2222, 3333, 4444]"));
        System.out.println(service.buildSchema("{\"a\":\"b\"}"));
        System.out.println(service.buildSchema("[true, null, 1111, \"1111\"]"));
        System.out.println(service.buildSchema("[\"11111\"]"));
        System.out.println(service.buildSchema("[{\"key\":{}}]"));
        System.out.println(service.buildSchema("[{\"key\":null}, {\"key\":{\"a\":\"b\"}}]"));
        System.out.println(service.buildSchema("[{\"key\":\"\"}]"));
        System.out.println(service.buildSchema("null"));
        System.out.println(service.buildSchema(""));
        json = String.valueOf(Long.MAX_VALUE);
        System.out.println(service.buildSchema(json));
        System.out.println(service.checkWithSchema(service.buildSchema(json), json));
        json = String.valueOf(Double.MAX_VALUE);
        System.out.println(service.buildSchema(json));
        System.out.println(service.checkWithSchema(service.buildSchema(json), json));

    }
}
