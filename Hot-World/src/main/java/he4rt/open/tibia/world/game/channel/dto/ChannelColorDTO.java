package he4rt.open.tibia.world.game.channel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class ChannelColorDTO {

    private String mydefault;
    private Map<String, String> byvocation;
}
