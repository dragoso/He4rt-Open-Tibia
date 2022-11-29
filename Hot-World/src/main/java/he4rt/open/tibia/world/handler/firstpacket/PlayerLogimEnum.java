package he4rt.open.tibia.world.handler.firstpacket;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PlayerLogimEnum implements IEnums {

    LOGIM_DEFFAUL(-1),
    LOGIM_ERRO(0x14),
    LOGIM_FIRST_PACKET(0x0006),
    LOGIM_CHAR_LIST(100);

    private final int value;

    public static PlayerLogimEnum getByIntegerValue(int opCode) {
        return Arrays.stream(PlayerLogimEnum.values()).filter(e -> Objects.equals(e.getValue(), opCode)).findFirst().orElse(PlayerLogimEnum.LOGIM_DEFFAUL);
    }
}
