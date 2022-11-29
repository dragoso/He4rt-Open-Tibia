package he4rt.open.tibia.world.handler.channel;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
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
@AGameHandlerCode(code = RecvOpcodeEnum.RECV_CHANNELOPEN)
public class OpenChannelHandler extends GameHandlerBase {

    @Autowired
    private PlayerStorage playerStorage;

    @Autowired
    private ChannelStorage channelStorage;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception {

        UserVO userVO = (UserVO) userAB;
        int chanelId = inputBaosVO.readShort();
        PlayerVO playerVO = userVO.getPlayerVO();

        if (Objects.isNull(playerVO) || !playerStorage.getPlayersStorage().containsKey(playerVO.getId())) {
            log.error("Jogador não encontrado. Id: <{}>", Objects.nonNull(playerVO) ? playerVO.getId() : "Não Registrado no cliente");
            return;
        }

        if (!channelStorage.getChannels().containsKey(chanelId)) {
            log.error("Canal de id <{}> não cadastrado, porem tentado usar pelo jogador <{}>.", chanelId, playerVO.getId());
            return;
        }

        ChannelVO channelVO = channelStorage.getChannels().get(chanelId);
        if (channelVO.getConnecteds().containsKey(playerVO.getId())) {

            userVO.send(ChannelPacket.encodeErro(userVO.getUserEntity().getLangue(), "Você já esta conectado a este canal.", ChannelMessageEnum.MESSAGE_STATUS_DEFFAULT));
            return;

        }

        OutputBaosVO outputBaosVO = new OutputBaosVO(userVO.getUserEntity().getLangue());
        ChannelPacket.encodeChannel(outputBaosVO, channelVO);

        if (Objects.nonNull(channelVO.getChannelDTO()) && Objects.nonNull(channelVO.getChannelDTO().getDescription())) {
            ChannelPacket.encodeMensage(outputBaosVO, channelVO.getId(), ChannelMessageEnum.MESSAGE_WHITE, channelVO.getChannelDTO().getDescription(), null);
        }

        userVO.send(outputBaosVO);


    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

    }

}
