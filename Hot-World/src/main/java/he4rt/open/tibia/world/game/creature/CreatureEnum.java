package he4rt.open.tibia.world.game.creature;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CreatureEnum implements IEnums {

    CREATURETYPE_UNK(-1),
    CREATURETYPE_PLAYER(0),
    CREATURETYPE_MONSTER(1),
    CREATURETYPE_NPC(2),
    CREATURETYPE_SUMMON_PLAYER(3),
    CREATURETYPE_SUMMON_OTHERS(4),
    CREATURETYPE_HIDDEN(5)
    ;

    private final int value;

    public static CreatureEnum getByIntegerValue(int opCode) {
        return Arrays.stream(CreatureEnum.values()).filter(v -> Objects.equals(opCode, v.getValue())).findFirst().orElse(CREATURETYPE_UNK);
    }
}