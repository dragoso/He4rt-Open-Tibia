package he4rt.open.tibia.base.data.enums;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum JobEnum implements IEnums {

    None(0),
    Sorcerer(1),
    Druid(2),
    Paladin(3),
    Knight(4),

    Master_Sorcerer(5),
    Elder_Druid(6),
    Royal_Paladin(7),
    Elite_Knight(8),

    Tutor(9),
    Game_Master(10),
    GOD(11)
    ;

    private final int value;

    public static JobEnum getByIntValue(int i) {
        return Arrays.stream(JobEnum.values()).filter(e -> e.getValue() == i).findFirst().orElse(null);
    }
}
