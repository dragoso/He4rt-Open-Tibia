package he4rt.open.tibia.logim.config;

import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.BufferProcessVO;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.game.ServerAB;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.logim.vo.UserVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static he4rt.open.tibia.base.core.io.constants.IOConstants.DECODER_SESSION;
import static he4rt.open.tibia.base.core.io.constants.IOConstants.USER_SESSION;

@Configuration
@Getter
@Slf4j
public class ServerVO extends ServerAB {

    @Override
    public void sessionOpened(IoSession session) {

        UserAB userAB = UserVO.builder().ioSession(session).build();
        session.setAttribute(USER_SESSION, userAB);
        session.setAttribute(DECODER_SESSION, BufferProcessVO.builder().build());
        getConnecteds().add(userAB);

    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        UserAB userAB = (UserAB) session.getAttribute(USER_SESSION);

        if (Objects.nonNull(userAB)) {
            InputBaosVO inputBaosVO = (InputBaosVO) message;

            RecvOpcodeEnum opCode = RecvOpcodeEnum.RECV_DEFAULT;

            if (handlers.containsKey(opCode)) {
                handlers.get(opCode).doExecutePacket(inputBaosVO, userAB);
            } else {
                log.debug("Novo pacote encontrado <{}>. Pacote : <{}>.", opCode, inputBaosVO);
            }
        }
    }
}

