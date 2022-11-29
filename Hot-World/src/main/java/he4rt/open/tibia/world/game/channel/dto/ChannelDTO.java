package he4rt.open.tibia.world.game.channel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class ChannelDTO {
    private boolean opened;
    private boolean enabled;

    private String name;
    private String description;

    private List<Integer> allowedvocations;

    private ChannelMuteRuleDTO muterule;
    private ChannelColorDTO color;
    private ChannelLevelsDTO allowedLevels;
}
