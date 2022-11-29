package he4rt.open.tibia.world.config;

import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.BufferProcessVO;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.data.enums.LocationEnum;
import he4rt.open.tibia.base.game.ServerAB;
import he4rt.open.tibia.world.game.UserVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Objects;

import static he4rt.open.tibia.base.core.io.constants.IOConstants.DECODER_SESSION;
import static he4rt.open.tibia.base.core.io.constants.IOConstants.USER_SESSION;
import static he4rt.open.tibia.world.handler.firstpacket.PlayerLogimPacket.encodeFirstPacket;

@Configuration
@Getter
@Slf4j
public class ServerVO extends ServerAB {

    @Autowired
    private IntegrationConfig integrationConfig;

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {

        UserVO userVO = (UserVO) session.getAttribute(USER_SESSION);

        if (Objects.nonNull(userVO)) {
            userVO.pinValidator();
        }
    }

    @Override
    public void sessionOpened(IoSession session) {

        UserVO userVO = UserVO.builder().integrationConfig(integrationConfig).ioSession(session).schedules(new HashMap()).build();
        session.setAttribute(USER_SESSION, userVO);
        session.setAttribute(DECODER_SESSION, BufferProcessVO.builder().build());
        getConnecteds().add(userVO);

        userVO.send(encodeFirstPacket(LocationEnum.EUA));
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        UserVO userVO = (UserVO) session.getAttribute(USER_SESSION);

        if (Objects.nonNull(userVO)) {
            synchronized (userVO) {

                InputBaosVO inputBaosVO = (InputBaosVO) message;

                RecvOpcodeEnum opCode;

                if (!userVO.isRecvFirstPacket()) {
                    opCode = RecvOpcodeEnum.RECV_DEFAULT;
                    userVO.setRecvFirstPacket(true);

                    short size = inputBaosVO.readShort();

                    if (size < 2) {

                        return;

                    }

                    inputBaosVO.setPosition(0);
                } else {
                    inputBaosVO.decript(userVO.getXteaSecurity());

                    if (inputBaosVO.getBytes().length < 9) {

                        opCode = RecvOpcodeEnum.RECV_UNKNOW;

                    } else {
                        inputBaosVO.skipBytes(6);
                        short size = inputBaosVO.readShort();

                        int type = inputBaosVO.readByte();

                        log.info("Novo pacote encontrado <{}>. ", type);
                        opCode = RecvOpcodeEnum.RECV_UNKNOW.getByIntegerValue(type);
                        inputBaosVO.setOpCode(opCode);
                    }
                }

                if (handlers.containsKey(opCode)) {
                    handlers.get(opCode).doExecutePacket(inputBaosVO, userVO);
                } else {
                    log.info("Novo pacote encontrado <{}>. Pacote : <{}>.", opCode, inputBaosVO);
                }
            }
        }
    }
}

