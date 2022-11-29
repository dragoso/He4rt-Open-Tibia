package he4rt.open.tibia.base.core.tibia;

import he4rt.open.tibia.base.utils.HashCodeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
public class Position {

    private int x;
    private int y;
    private int z;

    public Position(Position position, DirectionEnum directionEnum) {
        setX(position.getX());
        setY(position.getY());
        setZ(position.getZ());
        testMap();
        switch (directionEnum) {
            case NORTH:
                y--;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y++;
                break;
            case WEST:
                x--;
                break;
            case NORTHWEST:
                y--;
                x--;
                break;
            case NORTHEAST:
                y--;
                x++;
                break;
            case SOUTHWEST:
                y++;
                x--;
                break;
            case SOUTHEAST:
                y++;
                x++;
                break;
            case UP:
                z--;
                break;
            case DOWN:
                z++;
                break;
        }
    }

    public int getX() {
        testMap();
        return x;
    }

    public int getY() {
        testMap();
        return y;
    }

    public int getZ() {
        testMap();
        return z;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position pos = (Position) obj;
            return x == pos.x && y == pos.y && z == pos.z;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = HashCodeUtil.SEED;
        result = HashCodeUtil.hash(result, x);
        result = HashCodeUtil.hash(result, y);
        result = HashCodeUtil.hash(result, z);
        return result;
    }

    public String toString() {
        return String.format("(x: %s, y: %s, z: %s)", x, y, z);
    }

    public int distanceTo(Position position) {
        testMap();
        return Math.max(Math.abs(x - position.x), Math.abs(y - position.y));
    }

    public Position scale(int distance) {
        testMap();
        return Position.builder().x(x * distance).y(y * distance).z(z * distance).build();
    }

    public Position add(Position delta) {
        testMap();
        return Position.builder().x(x + delta.x).y(y + delta.y).z(z + delta.z).build();
    }

    public Position add(DirectionEnum directionEnum) {
        return new Position(this, directionEnum);
    }

    public int getZDistanceTo(Position position) {
        testMap();
        return Math.abs(z - position.z);
    }

    public DirectionEnum directionTo(Position position) {
        testMap();
        position.testMap();

        if (!equals(position)) {
            Position ref = position.add(invert());
            double angle = Math.atan2(ref.getY(), ref.getX());
            double delta = Math.PI / 8.0;
            if (between(angle, -delta, delta)) {
                return DirectionEnum.EAST;
            } else if (between(angle, delta, 3 * delta)) {
                return DirectionEnum.SOUTHEAST;
            } else if (between(angle, 3 * delta, 5 * delta)) {
                return DirectionEnum.SOUTH;
            } else if (between(angle, 5 * delta, 7 * delta)) {
                return DirectionEnum.SOUTHWEST;
            } else if (between(angle, -3 * delta, -delta)) {
                return DirectionEnum.NORTHEAST;
            } else if (between(angle, -5 * delta, -3 * delta)) {
                return DirectionEnum.NORTH;
            } else if (between(angle, -7 * delta, -5 * delta)) {
                return DirectionEnum.NORTHWEST;
            } else {
                return DirectionEnum.WEST;
            }
        } else {
            return DirectionEnum.NONE;
        }
    }

    private boolean between(double a, double b, double c) {
        return a >= b && a <= c;
    }

    public boolean isNextTo(Position position) {
        testMap();
        position.testMap();
        return getZDistanceTo(position) == 0 && distanceTo(position) <= 1;
    }

    public Position invert() {
        return Position.builder().x(-x).y(-y).z(-z).build();
    }

    /*
     * Methods for special positions.
     */

    public boolean isInventory() {
        return x == 0xFFFF && (y & 0x40) == 0;
    }

    public boolean isContainer() {
        return x == 0xFFFF && (y & 0x40) != 0;
    }

    private void testMap() {
        if (isInventory()) throw new IllegalStateException("Position is in inventory!");
        if (isContainer()) throw new IllegalStateException("Position is in container!");
    }

    public int getContainerId() {
        if (isContainer()) {
            return y & 0x0F;
        } else {
            throw new IllegalStateException("Position is not in container!");
        }
    }

    public int getContainerSlot() {
        if (isContainer()) {
            return z;
        } else {
            throw new IllegalStateException("Position is not in container!");
        }
    }

}
