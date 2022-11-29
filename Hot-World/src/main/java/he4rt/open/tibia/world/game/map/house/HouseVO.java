package he4rt.open.tibia.world.game.map.house;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class HouseVO {

    private final long id;
    private List<HouseTileVO> tiles = new ArrayList();

}
