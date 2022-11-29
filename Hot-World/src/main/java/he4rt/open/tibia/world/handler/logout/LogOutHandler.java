package he4rt.open.tibia.world.handler.logout;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.world.game.UserVO;
import he4rt.open.tibia.world.game.channel.ChannelStorage;
import he4rt.open.tibia.world.game.channel.ChannelVO;
import he4rt.open.tibia.world.game.player.PlayerStorage;
import he4rt.open.tibia.world.game.player.PlayerVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Log4j2
@AGameHandlerCode(code = RecvOpcodeEnum.RECV_PLAYERLOGOUT)
public class LogOutHandler extends GameHandlerBase {

    @Autowired
    private PlayerStorage playerStorage;

    @Autowired
    private ChannelStorage channelStorage;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception {

        UserVO userVO = (UserVO) userAB;
        PlayerVO playerVO = userVO.getPlayerVO();

        if (Objects.isNull(playerVO) || !playerStorage.getPlayersStorage().containsKey(playerVO.getId())) {
            log.error("Jogador não encontrado. Id: <{}>", Objects.nonNull(playerVO) ? playerVO.getId() : "Não Registrado no cliente");
            return;
        }

        for (ChannelVO channelVO : channelStorage.getChannels().values()) {
            channelVO.getConnecteds().remove(playerVO.getId());
        }

        playerStorage.getPlayersStorage().remove(playerVO.getId());

        userAB.disconnect();


    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

    }

}
