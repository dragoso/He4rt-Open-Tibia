package he4rt.open.tibia.world.game.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MapEnum {
    OTBM_ATTR_DESCRIPTION(1),
    OTBM_ATTR_EXT_FILE(2),
    OTBM_ATTR_TILE_FLAGS(3),
    OTBM_ATTR_ACTION_ID(4),
    OTBM_ATTR_UNIQUE_ID(5),
    OTBM_ATTR_TEXT(6),
    OTBM_ATTR_DESC(7),
    OTBM_ATTR_TELE_DEST(8),
    OTBM_ATTR_ITEM(9),
    OTBM_ATTR_DEPOT_ID(10),
    OTBM_ATTR_EXT_SPAWN_MONSTER_FILE(11),
    OTBM_ATTR_RUNE_CHARGES(12),
    OTBM_ATTR_EXT_HOUSE_FILE(13),
    OTBM_ATTR_HOUSEDOORID(14),
    OTBM_ATTR_COUNT(15),
    OTBM_ATTR_DURATION(16),
    OTBM_ATTR_DECAYING_STATE(17),
    OTBM_ATTR_WRITTENDATE(18),
    OTBM_ATTR_WRITTENBY(19),
    OTBM_ATTR_SLEEPERGUID(20),
    OTBM_ATTR_SLEEPSTART(21),
    OTBM_ATTR_CHARGES(22),
    OTBM_ATTR_EXT_SPAWN_NPC_FILE(23);

    private final int value;

    public static MapEnum getByInt(int value) {

        return Arrays.stream(MapEnum.values()).filter(v -> v.value == value).findFirst().orElse(null);

    }

};
