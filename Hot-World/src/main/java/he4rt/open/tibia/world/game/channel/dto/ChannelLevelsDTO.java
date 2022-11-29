package he4rt.open.tibia.world.game.channel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class ChannelLevelsDTO {

    public int biggerthen;
    public int lowerThen;
    public int only;

}
