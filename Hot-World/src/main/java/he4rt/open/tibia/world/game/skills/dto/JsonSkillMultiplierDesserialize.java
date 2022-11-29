package he4rt.open.tibia.world.game.skills.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import he4rt.open.tibia.base.data.enums.AbilityEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonSkillMultiplierDesserialize extends JsonDeserializer<Map> {

    @Override
    public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jsonParser.readValueAsTree();

        Map<AbilityEnum, Double> ret = new HashMap();

        node.forEach(n -> ret.put(AbilityEnum.valueOf(n.get("name").asText()), n.get("multiplier").asDouble()));

        return ret;
    }
}
