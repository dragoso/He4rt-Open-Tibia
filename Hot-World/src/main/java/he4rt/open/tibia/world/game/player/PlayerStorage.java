package he4rt.open.tibia.world.game.player;

import lombok.Getter;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerStorage {

    @Getter(onMethod_ = {@Synchronized})
    private Map<Long, PlayerVO> playersStorage = new HashMap();

}
