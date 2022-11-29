package he4rt.open.tibia.world.handler.channel;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ChannelMessageEnum implements IEnums {

    MESSAGE_NONE(0),
    MESSAGE_SAY(1),
    MESSAGE_WHISPER(2),
    MESSAGE_YELL(3),
    MESSAGE_PLAYER_NPC(4),
    MESSAGE_NPC_PLAYER(5),
    MESSAGE_PRIVATE(6),
    MESSAGE_YELLOW(7),
    MESSAGE_WHITE(8),
    MESSAGE_RVR_CHANNEL(9),
    MESSAGE_RVR_ANSWER(10),
    MESSAGE_RVR_CONTINUE(11),
    MESSAGE_BROADCAST(12),
    MESSAGE_RED(13),
    MESSAGE_PRIVATE_RED(14),
    MESSAGE_ORANGE(15),
    MESSAGE_RED2(17),
    MESSAGE_MONSTER(19),

    MESSAGE_STATUS_RED(18),
    MESSAGE_EVENT_ORAGENGE(19),
    MESSAGE_STATUS_ORANGE(20),
    MESSAGE_STATUS_WARNING(21),
    MESSAGE_EVENT_ADVANCE(22),
    MESSAGE_EVENT_DEFFAULT(23),
    MESSAGE_STATUS_DEFFAULT(24),
    MESSAGE_STATUS_DESCRIPTION(25),
    MESSAGE_STATUS_INFO(26),
    MESSAGE_STATUS_CONSOLE(27),

    ;

    private final int value;

    public static ChannelMessageEnum getByIntegerValue(int opCode) {
        return Arrays.stream(ChannelMessageEnum.values()).filter(e -> e.getValue() == opCode).findFirst().orElse(MESSAGE_NONE);
    }
}
