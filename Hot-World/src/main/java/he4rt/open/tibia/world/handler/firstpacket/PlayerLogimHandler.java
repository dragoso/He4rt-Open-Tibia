package he4rt.open.tibia.world.handler.firstpacket;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.core.security.RSASecurity;
import he4rt.open.tibia.base.core.security.XTEASecurity;
import he4rt.open.tibia.base.data.entity.UserEntity;
import he4rt.open.tibia.base.data.repository.PlayerAbilityRepository;
import he4rt.open.tibia.base.data.repository.PlayerPropertyRepository;
import he4rt.open.tibia.base.data.repository.PlayerRepository;
import he4rt.open.tibia.base.data.repository.UserRepository;
import he4rt.open.tibia.base.game.ServerAB;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.world.game.UserVO;
import he4rt.open.tibia.world.game.channel.ChannelStorage;
import he4rt.open.tibia.world.game.io.OutPacketProcessor;
import he4rt.open.tibia.world.game.map.MapStorage;
import he4rt.open.tibia.world.game.map.tile.TileVO;
import he4rt.open.tibia.world.game.player.PlayerStorage;
import he4rt.open.tibia.world.game.player.PlayerVO;
import he4rt.open.tibia.world.game.skills.VocationStorage;
import he4rt.open.tibia.world.game.skills.enums.EffectEnum;
import he4rt.open.tibia.world.handler.channel.ChannelPacket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

@Log4j2
@AGameHandlerCode(code = RecvOpcodeEnum.RECV_DEFAULT)
public class PlayerLogimHandler extends GameHandlerBase {

    @Autowired
    private VocationStorage vocationStorage;
    @Autowired
    private PlayerStorage playerStorage;
    @Autowired
    private ChannelStorage channelStorage;
    @Autowired
    private MapStorage mapStorage;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerAbilityRepository playerAbilityRepository;
    @Autowired
    private PlayerPropertyRepository playerPropertyRepository;


    @Autowired
    private ServerAB serverAB;
    @Autowired
    private RSASecurity rsaSecurity;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void executePacket(InputBaosVO inputBaosVO, UserAB userAB) throws Exception {

        UserVO userVO = (UserVO) userAB;

        int packetPayload = inputBaosVO.readShort();
        int tcpPayload = packetPayload + 2;

        inputBaosVO.skipBytes(5);

        int systemOperation = inputBaosVO.readShort();
        int protocolVersion = inputBaosVO.readShort();

        int encryptedDataLength = (tcpPayload - inputBaosVO.getPosition());
        byte[] encryptedData = inputBaosVO.readBytes(encryptedDataLength);

        inputBaosVO = rsaSecurity.decodeRSA(encryptedData);

        userAB.setXteaSecurity(new XTEASecurity());
        userAB.getXteaSecurity().init(new long[]{inputBaosVO.readInt(), inputBaosVO.readInt(), inputBaosVO.readInt(), inputBaosVO.readInt()});

        inputBaosVO.readByte();//Game master
        String nome = inputBaosVO.readString();
        String playerName = inputBaosVO.readString();
        String senha = inputBaosVO.readString();
        byte[] unknow = inputBaosVO.readBytes(5);

        UserEntity userEntity = userRepository.findByName(nome);

        if (Objects.isNull(userEntity)) {

            userAB.send(PlayerLogimPacket.encodeErro(userEntity, "Usuario não encontrado."));
            return;

        }

        if (!bCryptPasswordEncoder.matches(senha, userEntity.getPassword())) {

            userAB.send(PlayerLogimPacket.encodeErro(userEntity, "Senha incorreta."));
            return;

        }

        if (!Objects.equals(protocolVersion, serverAB.getVersion())) {
            userAB.send(PlayerLogimPacket.encodeErro(userEntity, String.format("Versão permitida <%s>, Versão recebida <%s>.", serverAB.getVersion(), protocolVersion)));
            return;
        }

        userEntity.setPlayers(List.of(playerRepository.findByNameAndUser(playerName, userEntity)));
        userEntity.getPlayers().forEach(p -> {
            p.setProperty(playerPropertyRepository.findAllByPlayer(p));
            p.setAbilitys(playerAbilityRepository.findAllByPlayer(p));
        });

        userVO.setUserEntity(userEntity);
        userVO.setPlayerVO(PlayerVO
                .builder()
                .userVO(userVO)
                .build());


        PlayerVO playerVO = userVO.getPlayerVO();

        OutputBaosVO outputBaosVO = new OutputBaosVO(userEntity.getLangue());

        channelStorage.getChannels().values().stream().filter(c -> c.getChannelDTO().isOpened()).forEach(c -> ChannelPacket.encodeChannel(outputBaosVO, c));
        OutPacketProcessor.encodeSelfAppear(outputBaosVO, playerVO);

        TileVO tileVO = mapStorage.getTiles().get(playerVO.getEntity().getPosition());

        if (!tileVO.getCreatures().stream().anyMatch(p -> Objects.equals(p.getId(), playerVO.getId()))) {
            tileVO.getCreatures().add(playerVO);
        }

        OutPacketProcessor.encodeMapInfos(outputBaosVO, playerVO, mapStorage);
        OutPacketProcessor.encodeWorldLight(outputBaosVO, 250, 215);// TODO CONSTANT?
        OutPacketProcessor.encodeCreatureLight(outputBaosVO, playerVO);
        OutPacketProcessor.encodePlayerStatus(outputBaosVO, playerVO, vocationStorage);
        OutPacketProcessor.encodeMagicEffect(outputBaosVO, playerVO.getEntity().getPosition(), EffectEnum.BUBBLEBLUE);
        OutPacketProcessor.encodeAbilitys(outputBaosVO, playerVO.getEntity(), vocationStorage);

        //TODO DC IN PLAYER ONLINE
        playerStorage.getPlayersStorage().put(playerVO.getId(), playerVO);
        userVO.send(outputBaosVO);


    }

    @Override
    protected void handlerException(Exception e, UserAB userAB) {

        userAB.send(PlayerLogimPacket.encodeErro(userAB.getUserEntity(),"Erro desconhecido. Entre em contato com a equipe."));

    }
}
