package he4rt.open.tibia.base.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GenderEnum {

    FEMALE(0),
    MALE(1);

    private int value;

    public static GenderEnum getByIntValue(int i) {
        return Arrays.stream(GenderEnum.values()).filter(e -> e.getValue() == i).findFirst().orElse(null);
    }
}