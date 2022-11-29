package he4rt.open.tibia.world.game.map.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ItemAtributesServerEnum {

    ATTR_NONE(0),
    ATTR_DESCRIPTION(1),
    ATTR_EXT_FILE(2),
    ATTR_TILE_FLAGS(3),
    ATTR_ACTION_ID(4),
    ATTR_UNIQUE_ID(5),
    ATTR_TEXT(6),
    ATTR_DESC(7),
    ATTR_TELE_DEST(8),
    ATTR_ITEM(9),
    ATTR_DEPOT_ID(10),
    ATTR_EXT_SPAWN_FILE(11),
    ATTR_RUNE_CHARGES(12),
    ATTR_EXT_HOUSE_FILE(13),
    ATTR_HOUSEDOORID(14),
    ATTR_COUNT(15),
    ATTR_DURATION(16),
    ATTR_DECAYING_STATE(17),
    ATTR_WRITTENDATE(18),
    ATTR_WRITTENBY(19),
    ATTR_SLEEPERGUID(20),
    ATTR_SLEEPSTART(21),
    ATTR_CHARGES(22),
    ATTR_CONTAINER_ITEMS(23),
    ATTR_NAME(24),
    ATTR_ARTICLE(25),
    ATTR_PLURALNAME(26),
    ATTR_WEIGHT(27),
    ATTR_ATTACK(28),
    ATTR_DEFENSE(29),
    ATTR_EXTRADEFENSE(30),
    ATTR_ARMOR(31),
    ATTR_HITCHANCE(32),
    ATTR_SHOOTRANGE(33),
    ATTR_CUSTOM_ATTRIBUTES(34),
    ATTR_DECAYTO(35),
    ;

    private final int value;

    public static ItemAtributesServerEnum getByInt(int type) {
        return Arrays.stream(ItemAtributesServerEnum.values()).filter(i -> Objects.equals(i.getValue(), type)).findFirst().orElse(ATTR_NONE);
    }

}
