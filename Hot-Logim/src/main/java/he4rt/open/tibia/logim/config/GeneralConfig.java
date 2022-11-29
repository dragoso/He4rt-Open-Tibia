package he4rt.open.tibia.logim.config;

import he4rt.open.tibia.base.config.BaseGeneralConfig;
import he4rt.open.tibia.base.core.tibia.DirectionEnum;
import he4rt.open.tibia.base.data.entity.*;
import he4rt.open.tibia.base.data.enums.*;
import he4rt.open.tibia.base.data.repository.PlayerRepository;
import he4rt.open.tibia.base.data.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class GeneralConfig extends BaseGeneralConfig {


    // USE SOMENTE PARA CRIAR UM USUARIO NO BANCO DE DADOS
    //@Bean
    public UserEntity inserNewUser(UserRepository userRepository) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        return userRepository.save(UserEntity
                .builder()
                .name("1")
                .password(bCryptPasswordEncoder.encode("1"))
                .email("1@1.com")
                .premiumDays(30)
                .build()
        );

    }

    // USE SOMENTE PARA CRIAR UM PLAYER NO BANCO DE DADOS
    //@Bean
    public PlayerEntity inserNewPlayer(UserRepository userRepository, PlayerRepository playerRepository) {

        UserEntity userEntity = userRepository.findTopByName("1");

        PlayerEntity player = PlayerEntity
                .builder()
                .name("Teste")
                .worldId(1)
                .chaseEnum(ChaseEnum.STAND)
                .genderEnum(GenderEnum.MALE)
                .gmEnum(GMEnum.ADM)
                .fightEnum(FightEnum.BALANCED)
                .jobEnum(JobEnum.GOD)
                .capacity(13000)
                .directionEnum(DirectionEnum.NORTH)
                .x(1025)
                .y(1025)
                .z(7)
                .health(100)
                .maxHealth(100)
                .mana(50)
                .maxHealth(50)
                .user(userEntity)
                .build();

        userEntity.setPlayers(List.of(player));

        player.setLook(PlayerLookEntity
                .builder()
                .player(player)
                .lookType(95)
                .lookAdddons(2)
                .lookHead(0)
                .lookBody(0)
                .lookLegs(0)
                .lookFeet(0)
                .build()
        );

        player.setProperty(Arrays
                .stream(PropertyEnum.values())
                .map(pe -> PlayerPropertyEntity
                        .builder()
                        .propertyEnum(pe)
                        .player(player)
                        .value(0)
                        .build())
                .collect(Collectors.toSet()));

        player.setAbilitys(Arrays
                .stream(AbilityEnum.values())
                .map(a -> PlayerAbilityEntity
                        .builder()
                        .abilityEnum(a)
                        .player(player)
                        .level(1)
                        .exp(0)
                        .build())
                .collect(Collectors.toSet()));

        playerRepository.save(player);

        return player;
    }
}


