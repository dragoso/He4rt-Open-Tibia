package he4rt.open.tibia.world.handler.pong;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.world.game.UserVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AGameHandlerCode(code = RecvOpcodeEnum.RECV_PING)
public class PongHandler extends GameHandlerBase {

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) {

        UserVO userVO = (UserVO) userAB;

        userVO.setLastPing(System.currentTimeMillis());

    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

    }

}
