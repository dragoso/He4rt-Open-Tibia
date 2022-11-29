package he4rt.open.tibia.world.game.map.item;

public enum WeaponType {
    NONE,
    SWORD,
    CLUB,
    AXE,
    SHIELD,
    DISTANCE,
    WAND,
    AMMUNITION;

    public boolean isMeleeWeapon() {
        switch(this) {
        case SWORD:
        case CLUB:
        case AXE:
            return true;
        default:
            return false;
        }
    }
}
