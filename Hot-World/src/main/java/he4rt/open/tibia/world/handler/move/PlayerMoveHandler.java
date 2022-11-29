package he4rt.open.tibia.world.handler.move;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.core.tibia.DirectionEnum;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.world.game.UserVO;
import he4rt.open.tibia.world.game.map.MapStorage;
import he4rt.open.tibia.world.game.player.PlayerVO;
import org.springframework.beans.factory.annotation.Autowired;


@AGameHandlerCode(code = {
        RecvOpcodeEnum.RECV_WALKEAST,
        RecvOpcodeEnum.RECV_WALKWEST,
        RecvOpcodeEnum.RECV_WALKSOUTH,
        RecvOpcodeEnum.RECV_WALKNORTH,
        RecvOpcodeEnum.RECV_WALKNORTEAST,
        RecvOpcodeEnum.RECV_WALKNORTHWEST,
        RecvOpcodeEnum.RECV_WALKSOUTHEAST,
        RecvOpcodeEnum.RECV_WALKSOUTHWEST,
})

public class PlayerMoveHandler extends GameHandlerBase {

    @Autowired
    private MapStorage mapStorage;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) {

        ((UserVO) userAB).getPlayerVO().move(getDirection(inputBaosVO.getOpCode()), mapStorage);

    }

    public DirectionEnum getDirection(RecvOpcodeEnum recvOpcodeEnum) {
        switch (recvOpcodeEnum) {
            case RECV_WALKEAST -> {
                return DirectionEnum.EAST;
            }
            case RECV_WALKSOUTH -> {
                return DirectionEnum.SOUTH;
            }
            case RECV_WALKWEST -> {
                return DirectionEnum.WEST;
            }
            case RECV_WALKNORTEAST -> {
                return DirectionEnum.NORTHEAST;
            }
            case RECV_WALKNORTHWEST -> {
                return DirectionEnum.NORTHWEST;
            }
            case RECV_WALKSOUTHEAST -> {
                return DirectionEnum.SOUTHEAST;
            }
            case RECV_WALKSOUTHWEST -> {
                return DirectionEnum.SOUTHWEST;
            }
            default -> {
                return DirectionEnum.NORTH;
            }
        }
    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {


    }
}
