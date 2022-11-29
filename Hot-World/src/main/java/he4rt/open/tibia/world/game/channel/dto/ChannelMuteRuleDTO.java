package he4rt.open.tibia.world.game.channel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class ChannelMuteRuleDTO {

    public String cancelmessage;
    public Integer messagescount;
    public Integer timetoblock;
    public Integer waittime;
    public Integer timemultiplier;
}
