package he4rt.open.tibia.logim.logim;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogimEnum implements IEnums {

    LOGIM_DEFFAUL(-1),
    LOGIM_ERRO(10),
    LOGIM_CHAR_LIST(100);

    private final int value;
}
