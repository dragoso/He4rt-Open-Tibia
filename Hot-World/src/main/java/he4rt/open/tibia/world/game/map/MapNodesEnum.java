package he4rt.open.tibia.world.game.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MapNodesEnum {
    OTBM_ROOTV1(1),
    OTBM_MAP_DATA(2),
    OTBM_ITEM_DEF(3),
    OTBM_TILE_AREA(4),
    OTBM_TILE(5),
    OTBM_ITEM(6),
    OTBM_TILE_SQUARE(7),
    OTBM_TILE_REF(8),
    OTBM_SPAWNS(9),
    OTBM_SPAWN_AREA(10),
    OTBM_MONSTER(11),
    OTBM_TOWNS(12),
    OTBM_TOWN(13),
    OTBM_HOUSETILE(14),
    OTBM_WAYPOINTS(15),
    OTBM_WAYPOINT(16),;

    private final int value;

    public static MapNodesEnum getByInt(int value) {

        return Arrays.stream(MapNodesEnum.values()).filter(v -> v.value == value).findFirst().orElse(null);

    }
}