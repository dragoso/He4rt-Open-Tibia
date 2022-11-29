package he4rt.open.tibia.base.game;

import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.core.security.XTEASecurity;
import he4rt.open.tibia.base.data.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

@Slf4j
@Getter
@Setter
@SuperBuilder
public abstract class UserAB {

    private IoSession ioSession;
    private XTEASecurity xteaSecurity;
    private UserEntity userEntity;

    public void send(OutputBaosVO outputBaosVO) {
        try {
            ioSession.write(IoBuffer.wrap(outputBaosVO.encode(xteaSecurity)));
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem ao cliente.", e);
        }
    }

    public void close() {
        ioSession.close(false);
    }

    public abstract void disconnect();

}
