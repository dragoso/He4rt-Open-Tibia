package he4rt.open.tibia.world.game.map.item;

public enum InventorySlot {
    WHEREEVER, HEAD, NECKLACE, BACKPACK, ARMOR, RIGHT, LEFT, LEGS, FEET, RING, AMMO;

    public static InventorySlot getSlot(int index) {
        switch(index) {
        case 1: return HEAD;
        case 2: return NECKLACE;
        case 3: return BACKPACK;
        case 4: return ARMOR;
        case 5: return RIGHT;
        case 6: return LEFT;
        case 7: return LEGS;
        case 8: return FEET;
        case 9: return RING;
        case 10: return AMMO;
        default:
            return null;
        }
    }

}
