package he4rt.open.tibia.base.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GMEnum {

    USER(0),
    TUTOR(1),
    GM(2),
    ADM(3);

    private int value;

    public boolean isGm() {
        return this.equals(GM) || this.equals(ADM);
    }
}