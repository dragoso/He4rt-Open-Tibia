package he4rt.open.tibia.base.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum FightEnum {

    ATTACK(1),
    BALANCED(2),
    DEFENCE(3);

    private int value;

    public static FightEnum getByIntValue(int i) {
        return Arrays.stream(FightEnum.values()).filter(e -> e.getValue() == i).findFirst().orElse(null);
    }
}