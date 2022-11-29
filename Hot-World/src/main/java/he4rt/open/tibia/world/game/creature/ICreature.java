package he4rt.open.tibia.world.game.creature;

import he4rt.open.tibia.base.core.tibia.DirectionEnum;

public interface ICreature {

    long getId();
    String getName();

    CreatureEnum getCreatureType();

    long getHealth();
    long getMaxHealth();

    DirectionEnum getDirection();

    int getLookType();
    int getLookHead();
    int getLookBody();
    int getLookLegs();
    int getLookFeet();
    int getLookAddons();

    int getSeed();

}
