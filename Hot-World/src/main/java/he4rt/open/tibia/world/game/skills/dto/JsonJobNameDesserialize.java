package he4rt.open.tibia.world.game.skills.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import he4rt.open.tibia.base.data.enums.JobEnum;

import java.io.IOException;

public class JsonJobNameDesserialize extends JsonDeserializer<JobEnum> {

    @Override
    public JobEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return JobEnum.valueOf(jsonParser.getValueAsString().replace(" ", "_"));
    }
}
