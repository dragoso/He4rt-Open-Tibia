package he4rt.open.tibia.world.game.map.item;

import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.world.game.io.BinaryNode;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static he4rt.open.tibia.world.game.map.item.ItemAttribute.*;

@Component
@Log4j2
@Getter
public class ItemComponent {

    private static final int FLAG_BLOCK_SOLID = 1,
            FLAG_BLOCK_PROJECTILE = 2,
            FLAG_BLOCK_PATHFIND = 4,
            FLAG_HAS_HEIGHT = 8,
            FLAG_USEABLE = 16,
            FLAG_PICKUPABLE = 32,
            FLAG_MOVEABLE = 64,
            FLAG_STACKABLE = 128,
            FLAG_FLOORCHANGEDOWN = 256,
            FLAG_FLOORCHANGENORTH = 512,
            FLAG_FLOORCHANGEEAST = 1024,
            FLAG_FLOORCHANGESOUTH = 2048,
            FLAG_FLOORCHANGEWEST = 4096,
            FLAG_ALWAYSONTOP = 8192,
            FLAG_READABLE = 16384,
            FLAG_ROTABLE = 32768,
            FLAG_HANGABLE = 65536,
            FLAG_VERTICAL = 131072,
            FLAG_HORIZONTAL = 262144,
            FLAG_CANNOTDECAY = 524288,
            FLAG_ALLOWDISTREAD = 1048576,
            FLAG_CORPSE = 2097152;


    public static final int ITEM_ATTR_SERVERID = 0x10,
            ITEM_ATTR_CLIENTID = 0x11,
            ITEM_ATTR_NAME = 0x12,
            ITEM_ATTR_DESCR = 0x13,
            ITEM_ATTR_SPEED = 0x14,
            ITEM_ATTR_SLOT = 0x15,
            ITEM_ATTR_MAXITEMS = 0x16,
            ITEM_ATTR_WEIGHT = 0x17,
            ITEM_ATTR_WEAPON = 0x18,
            ITEM_ATTR_AMU = 0x19,
            ITEM_ATTR_ARMOR = 0x1A,
            ITEM_ATTR_MAGLEVEL = 0x1B,
            ITEM_ATTR_MAGFIELDTYPE = 0x1C,
            ITEM_ATTR_WRITEABLE = 0x1D,
            ITEM_ATTR_ROTATETO = 0x1E,
            ITEM_ATTR_DECAY = 0x1F,
            ITEM_ATTR_SPRITEHASH = 0x20,
            ITEM_ATTR_MINIMAPCOLOR = 0x21,
            ITEM_ATTR_07 = 0x22,
            ITEM_ATTR_08 = 0x23,
            ITEM_ATTR_LIGHT = 0x24,
            ITEM_ATTR_DECAY2 = 0x25,
            ITEM_ATTR_WEAPON2 = 0x26,
            ITEM_ATTR_AMU2 = 0x27,
            ITEM_ATTR_ARMOR2 = 0x28,
            ITEM_ATTR_WRITEABLE2 = 0x29,
            ITEM_ATTR_LIGHT2 = 0x2A,
            ITEM_ATTR_TOPORDER = 0x2B,
            ITEM_ATTR_WRITEABLE3 = 0x2C;

    private Map<Integer, ItemType> itens = new HashMap();

    public void loadFromBinary(BinaryNode root) throws IOException {
        InputBaosVO inputBaosVO = root.generateInputBaos();

        /*long flags = */
        inputBaosVO.readInt();
        int attr = inputBaosVO.readByte();

        if (attr == 0x01) {
            /*int len = */
            inputBaosVO.readShort();
            /*long majorVersion = */
            inputBaosVO.readInt();
            /*long minorVersion = */
            inputBaosVO.readInt();
            /*long buildVersion = */
            inputBaosVO.readInt();
            byte[] CSDVersion = inputBaosVO.readBytes(128);
        }

        for (BinaryNode node : root.getChildren()) {
            inputBaosVO = node.generateInputBaos();

            int itemType = node.getType();
            ItemType.Group group = ItemType.Group.values()[itemType];
            ItemType iType = new ItemType(group);

            parseItemType(inputBaosVO, iType);

            itens.put(iType.getId(), iType);

        }
    }

    private boolean hasFlag(long flags, long flag) {
        return (flags & flag) == flag;
    }

    private void parseItemType(InputBaosVO inputBaosVO, ItemType iType)
            throws IOException {
        long flags = inputBaosVO.readInt();

        iType.setAttribute(BLOCKSOLID, hasFlag(flags, FLAG_BLOCK_SOLID));
        iType.setAttribute(BLOCKPROJECTILE, hasFlag(flags, FLAG_BLOCK_PROJECTILE));
        iType.setAttribute(BLOCKPATHFIND, hasFlag(flags, FLAG_BLOCK_PATHFIND));
        iType.setAttribute(HASHEIGHT, hasFlag(flags, FLAG_HAS_HEIGHT));
        iType.setAttribute(USEABLE, hasFlag(flags, FLAG_USEABLE));
        iType.setAttribute(PICKUPABLE, hasFlag(flags, FLAG_PICKUPABLE));
        iType.setAttribute(MOVEABLE, hasFlag(flags, FLAG_MOVEABLE));
        iType.setAttribute(STACKABLE, hasFlag(flags, FLAG_STACKABLE));
        iType.setAttribute(FLOORCHANGEDOWN, hasFlag(flags, FLAG_FLOORCHANGEDOWN));
        iType.setAttribute(FLOORCHANGENORTH, hasFlag(flags, FLAG_FLOORCHANGENORTH));
        iType.setAttribute(FLOORCHANGEEAST, hasFlag(flags, FLAG_FLOORCHANGEEAST));
        iType.setAttribute(FLOORCHANGESOUTH, hasFlag(flags, FLAG_FLOORCHANGESOUTH));
        iType.setAttribute(FLOORCHANGEWEST, hasFlag(flags, FLAG_FLOORCHANGEWEST));
        iType.setAttribute(ALWAYSONTOP, hasFlag(flags, FLAG_ALWAYSONTOP));
        iType.setAttribute(VERTICAL, hasFlag(flags, FLAG_VERTICAL));
        iType.setAttribute(HORIZONTAL, hasFlag(flags, FLAG_HORIZONTAL));
        iType.setAttribute(HANGABLE, hasFlag(flags, FLAG_HANGABLE));
        iType.setAttribute(DISTREAD, hasFlag(flags, FLAG_ALLOWDISTREAD));
        iType.setAttribute(ROTABLE, hasFlag(flags, FLAG_ROTABLE));
        iType.setAttribute(READABLE, hasFlag(flags, FLAG_READABLE));
        iType.setAttribute(CORPSE, hasFlag(flags, FLAG_CORPSE));

        while (inputBaosVO.available() > 0) {
            int attr = inputBaosVO.readByte();
            int len = inputBaosVO.readShort();

            switch (attr) {
                case ITEM_ATTR_SERVERID:
                    iType.serverId = inputBaosVO.readShort();
                    break;
                case ITEM_ATTR_CLIENTID:
                    iType.clientId = inputBaosVO.readShort();
                    break;
                case ITEM_ATTR_SPEED:
                    iType.baseSpeed = inputBaosVO.readShort();
                    break;
                case ITEM_ATTR_LIGHT2:
                    iType.lightLevel = inputBaosVO.readShort();
                    iType.lightColor = inputBaosVO.readShort();
                    break;
                case ITEM_ATTR_TOPORDER:
                    iType.alwaysOnTopOrder = inputBaosVO.readByte();
                    break;
                default:
                    //skip unknown attributes
                    int skipped = 0;

                    inputBaosVO.skipBytes(len);

                    break;
            }
        }
    }

    public ItemComponent(Properties configProperties) throws IOException {
        log.info("Iniciando desserialize dos itens...");
        BinaryNode root = BinaryNode.load(String.format("%s%s", configProperties.getProperty("directoryData"), "itens/items.otb"));

        loadFromBinary(root);
        log.info("Desserialize de itens terminado. Total de <{}> itens.", itens.size());
    }
}