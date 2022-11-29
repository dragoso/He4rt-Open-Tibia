package he4rt.open.tibia.world.handler.pong;

import he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.data.enums.LocationEnum;

public class PongPacket {

    public static OutputBaosVO encodePing(LocationEnum locationEnum) {
        OutputBaosVO outputBaosVO = new OutputBaosVO(locationEnum);

        outputBaosVO.write(SendOpcodeEnum.SEND_PING);

        return outputBaosVO;
    }
}
