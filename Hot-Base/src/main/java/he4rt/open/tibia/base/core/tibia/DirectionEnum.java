package he4rt.open.tibia.base.core.tibia;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum DirectionEnum implements IEnums {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3),
    SOUTHWEST(2),
    SOUTHEAST(2),
    NORTHWEST(0),
    NORTHEAST(0),
    UP(0),
    DOWN(2),
    NONE(0);

    private int value;

    public DirectionEnum combine(DirectionEnum directionEnum) {
        if (this == NONE) return directionEnum;
        else if (directionEnum == NONE) return this;
        else if (this == NORTH && directionEnum == EAST) return DirectionEnum.NORTHEAST;
        else if (this == NORTH && directionEnum == WEST) return DirectionEnum.NORTHWEST;
        else if (this == SOUTH && directionEnum == EAST) return DirectionEnum.SOUTHEAST;
        else if (this == SOUTH && directionEnum == WEST) return DirectionEnum.SOUTHWEST;

        else if (directionEnum == NORTH && this == EAST) return DirectionEnum.NORTHEAST;
        else if (directionEnum == NORTH && this == WEST) return DirectionEnum.NORTHWEST;
        else if (directionEnum == SOUTH && this == EAST) return DirectionEnum.SOUTHEAST;
        else if (directionEnum == SOUTH && this == WEST) return DirectionEnum.SOUTHWEST;

        else throw new IllegalArgumentException("Cannot combine directions " + this + " and " + directionEnum + ".");
    }

    public DirectionEnum invert() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case SOUTHWEST:
                return NORTHEAST;
            case SOUTHEAST:
                return NORTHWEST;
            case NORTHEAST:
                return SOUTHWEST;
            case NORTHWEST:
                return SOUTHEAST;
            default:
                return NONE;
        }
    }

    public DirectionEnum normalize() {
        switch (this) {
            case NORTHEAST:
            case SOUTHEAST:
                return DirectionEnum.EAST;
            case NORTHWEST:
            case SOUTHWEST:
                return DirectionEnum.WEST;
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return this;
            default:
                return DirectionEnum.NONE;
        }
    }

    public boolean isDiagonal() {
        switch (this) {
            case NORTHWEST:
            case NORTHEAST:
            case SOUTHWEST:
            case SOUTHEAST:
                return true;
            default:
                return false;
        }
    }

    public boolean contains(DirectionEnum dir) {
        if (dir.isDiagonal() || dir.isZ() || dir == NONE) {
            throw new IllegalArgumentException("A Direction can not contain a diagonal, none or Z direction.");
        }
        if (this == dir) {
            return true;
        } else {
            for (DirectionEnum d : Arrays.asList(NORTH, EAST, SOUTH, WEST)) {
                if (d != dir && d.invert() != dir && d.combine(dir) == this) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isZ() {
        switch (this) {
            case UP:
            case DOWN:
                return true;
            default:
                return false;
        }
    }

}
