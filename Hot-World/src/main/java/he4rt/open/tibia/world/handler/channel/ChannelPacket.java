package he4rt.open.tibia.world.handler.channel;

import he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.data.enums.AbilityEnum;
import he4rt.open.tibia.base.data.enums.LocationEnum;
import he4rt.open.tibia.world.game.channel.ChannelVO;
import he4rt.open.tibia.world.game.creature.ICreature;
import he4rt.open.tibia.world.game.player.PlayerVO;

import java.util.Objects;

import static he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum.SEND_TEXTMESSAGE;


public class ChannelPacket {

    public static void encodeLogOut(OutputBaosVO outputBaosVO, long id) {

        outputBaosVO.write(SendOpcodeEnum.SEND_CLOSECHANNEL);
        outputBaosVO.writeShort(id);

    }
    public static OutputBaosVO encodeErro(LocationEnum locationEnum, String text, ChannelMessageEnum type) {
        OutputBaosVO outputBaosVO = new OutputBaosVO(locationEnum);

        outputBaosVO.write(SEND_TEXTMESSAGE);
        outputBaosVO.write(type);
        outputBaosVO.writeString(text);

        return outputBaosVO;
    }

    public static void encodeChannel(OutputBaosVO outputBaosVO, ChannelVO channelVO) {

        outputBaosVO.write(SendOpcodeEnum.SEND_OPENCHANNEL);
        outputBaosVO.writeShort(channelVO.getId());
        outputBaosVO.writeString(channelVO.getChannelDTO().getName());

    }

    public static void encodeMensage(OutputBaosVO outputBaosVO, long id, ChannelMessageEnum channelMessageEnum, String text, ICreature creature) {

        outputBaosVO.write(SendOpcodeEnum.SEND_SENDPRIVATEMESSAGE);
        outputBaosVO.writeInt(0);

        outputBaosVO.writeString(Objects.nonNull(creature) ? creature.getName() : "");
        outputBaosVO.writeShort(Objects.nonNull(creature) && creature instanceof PlayerVO ? ((PlayerVO) creature).getEntity().getAbilityByType(AbilityEnum.LEVEL).getLevel() : 0);

        outputBaosVO.write(channelMessageEnum);
        outputBaosVO.writeShort(id);
        outputBaosVO.writeString(text);

    }
}
