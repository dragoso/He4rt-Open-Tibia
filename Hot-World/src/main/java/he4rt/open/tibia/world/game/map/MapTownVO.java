package he4rt.open.tibia.world.game.map;

import he4rt.open.tibia.base.core.tibia.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class MapTownVO {

    private final int id;
    private final String name;
    private final Position temple;

}
