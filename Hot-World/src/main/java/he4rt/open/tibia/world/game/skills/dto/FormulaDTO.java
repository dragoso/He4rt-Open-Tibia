package he4rt.open.tibia.world.game.skills.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@SuperBuilder
@Jacksonized
public class FormulaDTO {
    private float meleeDamage;
    private float distDamage;
    private float defense;
    private float armor;
}
