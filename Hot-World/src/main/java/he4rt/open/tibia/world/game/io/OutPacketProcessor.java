package he4rt.open.tibia.world.game.io;

import he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.core.tibia.Position;
import he4rt.open.tibia.base.data.entity.PlayerEntity;
import he4rt.open.tibia.base.data.enums.AbilityEnum;
import he4rt.open.tibia.base.data.enums.PropertyEnum;
import he4rt.open.tibia.world.game.map.MapStorage;
import he4rt.open.tibia.world.game.player.PlayerVO;
import he4rt.open.tibia.world.game.skills.VocationStorage;
import he4rt.open.tibia.world.game.skills.enums.EffectEnum;

public class OutPacketProcessor {

    public static void encodeSelfAppear(OutputBaosVO outputBaosVO, PlayerVO playerVO) {

        outputBaosVO.write(SendOpcodeEnum.SEND_SELFAPPEAR);
        outputBaosVO.writeInt(playerVO.getId());
        outputBaosVO.writeShort(50);
        outputBaosVO.write(playerVO.getEntity().isReports());

    }


    public static void encodeCreatureLight(OutputBaosVO outputBaosVO, PlayerVO playerVO) {

        outputBaosVO.write(SendOpcodeEnum.SEND_CREATURELIGHT);
        outputBaosVO.writeInt(playerVO.getId());
        outputBaosVO.write((int) playerVO.getEntity().getPropertyByType(PropertyEnum.LIGHT_BRIGHTNESS).getValue());
        outputBaosVO.write((int) playerVO.getEntity().getPropertyByType(PropertyEnum.LIGHT_COLOR).getValue());

    }

    public static void encodeMapInfos(OutputBaosVO outputBaosVO, PlayerVO playerVO, MapStorage mapStorage) {

        outputBaosVO.write(SendOpcodeEnum.SEND_MAP_DESCRIPTION);
        Position pos = playerVO.getEntity().getPosition();
        outputBaosVO.writePosition(pos);
        mapStorage.getMapArea(pos.getX() - 8, pos.getY() - 6, pos.getZ(), outputBaosVO);

    }

    public static void encodeWorldLight(OutputBaosVO outputBaosVO, int color, int level) {

        outputBaosVO.write(SendOpcodeEnum.SEND_WORLDLIGHT);
        outputBaosVO.write(color);
        outputBaosVO.write(level);

    }

    public static void encodePlayerStatus(OutputBaosVO outputBaosVO, PlayerVO playerVO, VocationStorage vocationStorage) {

        outputBaosVO.write(SendOpcodeEnum.SEND_PLAYERSTATUS);
        outputBaosVO.writeShort(Math.min(Short.MAX_VALUE, playerVO.getEntity().getHealth()));
        outputBaosVO.writeShort(Math.min(Short.MAX_VALUE, playerVO.getEntity().getMaxHealth()));

        outputBaosVO.writeInt((long) playerVO.getEntity().getCapacity() * 100);

        outputBaosVO.writeInt((long) playerVO.getEntity().getAbilityByType(AbilityEnum.LEVEL).getExp());
        outputBaosVO.writeShort(playerVO.getEntity().getAbilityByType(AbilityEnum.LEVEL).getLevel());
        outputBaosVO.write(1);// exp multiplica por ?
        outputBaosVO.writeShort(Math.min(Short.MAX_VALUE, playerVO.getEntity().getMana()));
        outputBaosVO.writeShort(Math.min(Short.MAX_VALUE, playerVO.getEntity().getMaxMana()));

        outputBaosVO.write((int) playerVO.getEntity().getAbilityByType(AbilityEnum.MAGIC).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerVO.getEntity().getAbilityByType(AbilityEnum.MAGIC), playerVO.getEntity().getJobEnum()));

        outputBaosVO.write((int) playerVO.getEntity().getPropertyByType(PropertyEnum.SOUL).getValue());
        outputBaosVO.writeShort((int) playerVO.getEntity().getPropertyByType(PropertyEnum.STAMINA).getValue());

    }

    public static void encodeMagicEffect(OutputBaosVO outputBaosVO, Position position, EffectEnum effectEnum) {

        outputBaosVO.write(SendOpcodeEnum.SEND_MAGICEFFECT);
        outputBaosVO.writePosition(position);
        outputBaosVO.write(effectEnum);

    }

    public static void encodeAbilitys(OutputBaosVO outputBaosVO, PlayerEntity playerEntity, VocationStorage vocationStorage) {

        outputBaosVO.write(SendOpcodeEnum.SEND_PLAYERSKILLS);

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.FIST).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.FIST), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.CLUB).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.CLUB), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.SWORD).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.SWORD), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.AXE).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.AXE), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.DISTANCE).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.DISTANCE), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.SHILDING).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.SHILDING), playerEntity.getJobEnum()));

        outputBaosVO.write((int) playerEntity.getAbilityByType(AbilityEnum.FISHING).getLevelFull());
        outputBaosVO.write((byte) vocationStorage.getPercent(playerEntity.getAbilityByType(AbilityEnum.FISHING), playerEntity.getJobEnum()));

    }
}
