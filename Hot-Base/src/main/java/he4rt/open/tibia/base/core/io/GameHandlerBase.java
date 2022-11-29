package he4rt.open.tibia.base.core.io;

import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.game.UserAB;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GameHandlerBase {

    protected abstract void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception;

    public void doExecutePacket(InputBaosVO inputBaosVO, UserAB userAB) {

        try {

            executePacket(inputBaosVO, userAB);

        } catch (Exception e) {

            log.error("Erro ao processar pacote <{}>. Erro ", inputBaosVO, e);
            handlerException(e, userAB);

        }

    }

    protected abstract void handlerException(Exception e, UserAB userAB);

}
