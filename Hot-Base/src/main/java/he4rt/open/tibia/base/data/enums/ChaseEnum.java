package he4rt.open.tibia.base.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ChaseEnum {

    STAND(0),
    FOLLOW(1);

    private int value;

    public static ChaseEnum getByIntValue(int i) {
        return Arrays.stream(ChaseEnum.values()).filter(e -> e.getValue() == i).findFirst().orElse(null);
    }
}