package he4rt.open.tibia.world.game.skills.enums;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EffectEnum implements IEnums {

    XBLOOD(1),
    RINGSBLUE(2),
    PUFF(3),
    SPARKYELLOW(4),
    DAMAGEEXPLOSION(5),
    DAMAGEMAGICMISSILE(6),
    AREAFLAME(7),
    RINGSYELLOW(8),
    RINGSGREEN(9),
    XGRAY(10),
    BUBBLEBLUE(11),
    DAMAGEENERGY(12),
    GLITTERBLUE(13),
    GLITTERRED(14),
    GLITTERGREEN(15),
    FLAME(16),
    XPOISON(17),
    BUBBLEBLACK(18),
    SOUNDGREEN(19),
    SOUNDRED(20),
    DAMAGEVENOMMISSILE(21),
    SOUNDYELLOW(22),
    SOUNDPURPLE(23),
    SOUNDBLUE(24),
    SOUNDWHITE(25),

    NONE(255),
    BUBBLES(26),
    CRAPS(27),
    GIFTWRAPS(28),
    FIREWORKYELLOW(29),
    FIREWORKRED(30),
    FIREWORKBLUE(31),
    STUN(32),
    SLEEP(33),
    WATERCREATURE(34),
    GROUNDSHAKER(35),
    HEARTS(36),
    FIREATTACK(37),
    ENERGYAREA(38),
    SMALLCLOUDS(39),
    HOLYDAMAGE(40),
    BIGCLOUDS(41),
    ICEAREA(42),
    ICERORNADO(43),
    ICEATTACK(44),
    STONES(45),
    SMALLPLANTS(46),
    CARNIPHILA(47),
    PURPLEENERGY(48),
    YELLOWENERGY(49),
    HOLYAREA(50),
    BIGPLANTS(51),
    CAKE(52),
    GIANTICE(53),
    WATERSPLASH(54),
    PLANTATTACK(55),
    TUTORIALARROW(56),
    TUTORIALSQUARE(57),
    MIRRORHORIZONTAL(58),
    MIRRORVERTICAL(59),
    SKULLHORIZONTAL(60),
    SKULLVERTICAL(61),
    ASSASSIN(62),
    STEPSHORIZONTAL(63),
    BLOODYSTEPS(64),
    STEPSVERTICAL(65),
    YALAHARIGHOST(66),
    BATS(67),
    SMOKE(68),
    INSECTS(69),
    DRAGONHEAD(70),
    ;

    private final int value;

    public static EffectEnum getByIntegerValue(int opCode) {
        return Arrays.stream(EffectEnum.values()).filter(effectEnum -> Objects.equals(effectEnum.getValue(), opCode)).findFirst().orElse(null);
    }
}
