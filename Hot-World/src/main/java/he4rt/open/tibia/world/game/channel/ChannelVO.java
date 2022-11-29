package he4rt.open.tibia.world.game.channel;

import he4rt.open.tibia.world.game.channel.dto.ChannelDTO;
import he4rt.open.tibia.world.game.player.PlayerVO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class ChannelVO {

    private int id;
    private ChannelDTO channelDTO;
    private Map<Long, PlayerVO> connecteds;

}
