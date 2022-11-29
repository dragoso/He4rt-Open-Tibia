package he4rt.open.tibia.world.game.map.item;

public class Light {
    private int level;
    private int color;

    public Light(int level, int color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }
}