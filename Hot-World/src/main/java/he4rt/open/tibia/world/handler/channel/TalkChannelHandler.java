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
@AGameHandlerCode(code = RecvOpcodeEnum.RECV_SPEECH)
public class TalkChannelHandler extends GameHandlerBase {

    @Autowired
    private PlayerStorage playerStorage;

    @Autowired
    private ChannelStorage channelStorage;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception {

        UserVO userVO = (UserVO) userAB;

        ChannelMessageEnum channelMessageEnum = ChannelMessageEnum.MESSAGE_BROADCAST.getByIntegerValue(inputBaosVO.readByte());

        String receiver = "";
        Integer channelId = 0;

        switch (channelMessageEnum) {
            case MESSAGE_NONE -> {
                return;
            }

            case MESSAGE_PRIVATE, MESSAGE_PRIVATE_RED -> receiver = inputBaosVO.readString();

            case MESSAGE_YELLOW, MESSAGE_RED, MESSAGE_ORANGE -> channelId = (int) inputBaosVO.readShort();
        }

        String message = inputBaosVO.readString().trim();

        PlayerVO playerVO = userVO.getPlayerVO();

        if (Objects.isNull(playerVO) || !playerStorage.getPlayersStorage().containsKey(playerVO.getId())) {
            log.error("Jogador não encontrado. Id: <{}>", Objects.nonNull(playerVO) ? playerVO.getId() : "Não Registrado no cliente");
            return;
        }

        if (!channelStorage.getChannels().containsKey(channelId)) {
            log.error("Canal de id <{}> não cadastrado, porem tentado usar pelo jogador <{}>.", channelId, playerVO.getId());
            return;
        }

        ChannelVO channelVO = channelStorage.getChannels().get(channelId);
        if (!channelVO.getConnecteds().containsKey(playerVO.getId())) {
            log.error("Canal de id <{}> jogador <{}> tentando enviar uma mensagem porem ele não esta no canal.", channelId, playerVO.getId());
            return;
        }

        if (message.length() > 255) {
            log.error("Canal de id <{}> jogador <{}> tentando enviar uma mensagem maior que o permitido. Tamanho da menssage <{}>.", channelId, playerVO.getId(), message.length());
            return;
        }

        if (receiver.length() > 30) {
            log.error("Canal de id <{}> jogador <{}> tentando enviar uma mensagem com seu nome maior que o permitido. Tamanho do nome do jogador <{}>.", channelId, playerVO.getId(), receiver.length());
            return;
        }

        //Checar magia

        OutputBaosVO outputBaosVO = new OutputBaosVO(userVO.getUserEntity().getLangue());


        userVO.send(outputBaosVO);


    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

    }

}
