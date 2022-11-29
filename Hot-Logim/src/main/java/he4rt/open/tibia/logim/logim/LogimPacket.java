package he4rt.open.tibia.logim.logim;

import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.data.entity.PlayerEntity;
import he4rt.open.tibia.base.data.entity.UserEntity;
import he4rt.open.tibia.base.data.enums.LocationEnum;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class LogimPacket {

    public static OutputBaosVO encodeErro(UserEntity userEntity, String erro) {
        OutputBaosVO outputBaosVO = new OutputBaosVO(Objects.nonNull(userEntity) ? userEntity.getLangue() : LocationEnum.EUA);

        outputBaosVO.write(LogimEnum.LOGIM_ERRO);
        outputBaosVO.writeString(erro);

        return outputBaosVO;
    }

    public static OutputBaosVO encodePlayers(UserEntity userEntity) throws UnknownHostException {
        OutputBaosVO outputBaosVO = new OutputBaosVO(userEntity.getLangue());

        outputBaosVO.write(LogimEnum.LOGIM_CHAR_LIST);
        outputBaosVO.write(userEntity.getPlayers().size());

        for (PlayerEntity playerEntity : userEntity.getPlayers()) {

            outputBaosVO.writeString(playerEntity.getName());
            outputBaosVO.writeString(playerEntity.getWorldEntity().getName());
            outputBaosVO.write(InetAddress.getByName(playerEntity.getWorldEntity().getIp()).getAddress());
            outputBaosVO.writeShort(playerEntity.getWorldEntity().getPort());

        }

        outputBaosVO.writeShort(userEntity.getPremiumDays());
        return outputBaosVO;
    }
}
