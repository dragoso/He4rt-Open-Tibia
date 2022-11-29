package he4rt.open.tibia.world.game.player;

import he4rt.open.tibia.base.core.tibia.DirectionEnum;
import he4rt.open.tibia.base.data.entity.PlayerEntity;
import he4rt.open.tibia.base.data.enums.PropertyEnum;
import he4rt.open.tibia.world.game.UserVO;
import he4rt.open.tibia.world.game.creature.CreatureEnum;
import he4rt.open.tibia.world.game.creature.ICreature;
import he4rt.open.tibia.world.game.map.MapStorage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PlayerVO implements ICreature {

    private UserVO userVO;

    public PlayerEntity getEntity() {

        return userVO.getUserEntity().getPlayers().get(0);

    }

    public void move(DirectionEnum directionEnum, MapStorage mapStorage){

        if(!isAlive()) return;

        //TODO VERIFICAR SE PODE ANDAR E A VELOCIDADE


    }

    public boolean isAlive() {

        return getEntity().getHealth() > 0;

    }

    @Override
    public long getId() {
        return 268435456 + getEntity().getId();
    }

    @Override
    public CreatureEnum getCreatureType() {
        return CreatureEnum.CREATURETYPE_PLAYER;
    }

    @Override
    public String getName() {
        return getEntity().getName();
    }

    @Override
    public long getHealth() {
        return getEntity().getHealth();
    }

    @Override
    public long getMaxHealth() {
        return getEntity().getMaxHealth();
    }

    @Override
    public DirectionEnum getDirection() {
        return getEntity().getDirectionEnum();
    }

    @Override
    public int getLookType() {
        return getEntity().getLook().getLookType();
    }

    @Override
    public int getLookHead() {
        return getEntity().getLook().getLookHead();
    }

    @Override
    public int getLookBody() {
        return getEntity().getLook().getLookBody();
    }

    @Override
    public int getLookLegs() {
        return getEntity().getLook().getLookLegs();
    }

    @Override
    public int getLookFeet() {
        return getEntity().getLook().getLookFeet();
    }

    @Override
    public int getLookAddons() {
        return getEntity().getLook().getLookAdddons();
    }

    @Override
    public int getSeed() {
        return (int) getEntity().getPropertyByType(PropertyEnum.SPEED).getValue();
    }

}
