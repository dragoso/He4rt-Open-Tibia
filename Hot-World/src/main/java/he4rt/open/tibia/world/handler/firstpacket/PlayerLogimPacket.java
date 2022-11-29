package he4rt.open.tibia.world.handler.firstpacket;

import he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.data.entity.UserEntity;
import he4rt.open.tibia.base.data.enums.LocationEnum;

import java.util.Objects;

import static he4rt.open.tibia.world.handler.firstpacket.PlayerLogimEnum.LOGIM_ERRO;
import static he4rt.open.tibia.world.handler.firstpacket.PlayerLogimEnum.LOGIM_FIRST_PACKET;

public class PlayerLogimPacket {

    public static OutputBaosVO encodeErro(UserEntity userEntity, String erro) {
        OutputBaosVO outputBaosVO = new OutputBaosVO(Objects.isNull(userEntity) ? userEntity.getLangue() : LocationEnum.EUA);

        outputBaosVO.write(LOGIM_ERRO);
        outputBaosVO.writeString(erro);

        return outputBaosVO;
    }

    public static OutputBaosVO encodeFirstPacket(LocationEnum locationEnum) {

        OutputBaosVO outputBaosVO = new OutputBaosVO(locationEnum);
        outputBaosVO.setEncode(false);
        outputBaosVO.writeShort(LOGIM_FIRST_PACKET);
        outputBaosVO.write(0x1F);
        outputBaosVO.writeInt(System.currentTimeMillis());
        outputBaosVO.write((byte) (Math.random() * 255));

        return outputBaosVO;

    }

    public static OutputBaosVO encodePing(LocationEnum locationEnum) {
        OutputBaosVO outputBaosVO = new OutputBaosVO(locationEnum);

        outputBaosVO.write(SendOpcodeEnum.SEND_PING);

        return outputBaosVO;
    }
}
