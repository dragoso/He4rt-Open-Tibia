package he4rt.open.tibia.world.game.map.house;

import he4rt.open.tibia.world.game.map.tile.TileVO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class HouseTileVO extends TileVO {

    private final HouseVO houseVO;

}
