package he4rt.open.tibia.logim.logim;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.core.security.RSASecurity;
import he4rt.open.tibia.base.core.security.XTEASecurity;
import he4rt.open.tibia.base.data.entity.UserEntity;
import he4rt.open.tibia.base.data.repository.PlayerRepository;
import he4rt.open.tibia.base.data.repository.UserRepository;
import he4rt.open.tibia.base.data.repository.WorldRepository;
import he4rt.open.tibia.base.game.ServerAB;
import he4rt.open.tibia.base.game.UserAB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@AGameHandlerCode(code = RecvOpcodeEnum.RECV_DEFAULT)
public class LogimHandler extends GameHandlerBase {

    @Autowired
    private ServerAB serverAB;
    @Autowired
    private RSASecurity rsaSecurity;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception {

        int packetPayload = inputBaosVO.readShort();
        int tcpPayload = packetPayload + 2;

        inputBaosVO.skipBytes(7);

        int protocolVersion = inputBaosVO.readShort();

        if (!Objects.equals(protocolVersion, serverAB.getVersion())) {
            userAB.close();
            throw new LogimException(String.format("Versão permitida <%s>, Versão recebida <%s>.", serverAB.getVersion(), protocolVersion));
        }

        inputBaosVO.skipBytes(12);

        int encryptedDataLength = (tcpPayload - inputBaosVO.getPosition());
        byte[] encryptedData = inputBaosVO.readBytes(encryptedDataLength);

        inputBaosVO = rsaSecurity.decodeRSA(encryptedData);

        userAB.setXteaSecurity(new XTEASecurity());
        userAB.getXteaSecurity().init(new long[]{inputBaosVO.readInt(), inputBaosVO.readInt(), inputBaosVO.readInt(), inputBaosVO.readInt()});

        String nome = inputBaosVO.readString();
        String senha = inputBaosVO.readString();

        UserEntity userEntity = userRepository.findByName(nome);

        if (Objects.isNull(userEntity)) {

            userAB.send(LogimPacket.encodeErro(userEntity, "User not found."));
            return;

        }

        if (!bCryptPasswordEncoder.matches(senha, userEntity.getPassword())) {

            userAB.send(LogimPacket.encodeErro(userEntity, "Senha incorreta."));
            return;

        }

        userEntity.setPlayers(playerRepository.findAllByUser(userEntity));
        userEntity.getPlayers().forEach(p -> p.setWorldEntity(worldRepository.findById(p.getId()).get()));

        userAB.send(LogimPacket.encodePlayers(userEntity));
    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

        userAB.send(LogimPacket.encodeErro(userAB.getUserEntity(), "Erro desconhecido. Entre em contato com a equipe."));

    }
}
