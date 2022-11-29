package he4rt.open.tibia.base.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocationEnum {

    BRA("ISO-8859-1"),
    EUA("UTF-8"),
    POL("ISO-8859-2");

    private String value;

}
